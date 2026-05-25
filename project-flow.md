# NATURAL METALS MINING TRANSPARENCY SYSTEM (NMTS)
## COMPLETE END-TO-END SYSTEM FLOW (Explanatory)

---

## 🏗️ SYSTEM ARCHITECTURE OVERVIEW

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                                    EXTERNAL CLIENTS                                      │
│  (Murdock Metals Ltd, Chen Electronics Inc, Captain Mensah, Emma Williams, David Okafor) │
└─────────────────────────────────────────┬───────────────────────────────────────────────┘
                                          │
                                          ▼
                          ┌───────────────────────────────┐
                          │      API GATEWAY (port 8080)   │
                          │  • JWT Authentication Filter  │
                          │  • Route Requests to Services │
                          └───────────────┬───────────────┘
                                          │
              ┌───────────────────────────┼───────────────────────────┐
              │                           │                           │
              ▼                           ▼                           ▼
    ┌─────────────────┐         ┌─────────────────┐         ┌─────────────────┐
    │  auth-service   │         │  user-service   │         │  other services │
    │   (port 8081)   │◄───────►│   (port 8082)   │         │ (ports 8083-89) │
    └─────────────────┘  Feign  └─────────────────┘         └─────────────────┘
            │                           │                           │
            │                           │                           │
            └───────────────────────────┼───────────────────────────┘
                                        │
                                        ▼
                          ┌───────────────────────────────┐
                          │         KAFKA CLUSTER         │
                          │   (Event Streaming Platform)  │
                          └───────────────┬───────────────┘
                                          │
                                          ▼
                          ┌───────────────────────────────┐
                          │    analytics-service (8089)   │
                          │      MongoDB Event Store      │
                          └───────────────────────────────┘
```

---

## 🔄 FLOW 1: MINING AGENCY REGISTRATION & COMPANY CREATION

### The Complete Flow from Start to Finish

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 1: Murdock Metals Ltd decides to register on NMTS platform                        │
│ (John Murdock, the agency owner, submits registration on behalf of his company)        │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /auth/register
                           {
                             "name": "Murdock Metals Ltd",
                             "email": "contact@murdockmetals.com",
                             "password": "SecurePass123!",
                             "role": "MINING_AGENCY"
                           }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│                                                                                          │
│  • Receives request on /auth/register                                                    │
│  • This endpoint is in the SKIP list (no JWT validation required)                       │
│  • Routes to: lb://auth-service (load balanced via Eureka)                              │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            AUTH-SERVICE (port 8081)                                      │
│                                                                                          │
│  AuthController.register() receives the request:                                        │
│                                                                                          │
│  1. Checks if email already exists in nmts_auth database                                │
│     → SELECT * FROM auth_credential WHERE email = 'contact@murdockmetals.com'          │
│     → No existing record → proceed                                                      │
│                                                                                          │
│  2. BCrypt.hashpw("SecurePass123!") → generates secure hash                            │
│                                                                                          │
│  3. Saves AuthCredential entity to MySQL (schema: nmts_auth):                           │
│     INSERT INTO auth_credential (id, email, password_hash, role, is_active, created_at)│
│     VALUES (uuid_generated(), 'contact@murdockmetals.com', '$2a$10$...',               │
│             'MINING_AGENCY', true, NOW())                                               │
│                                                                                          │
│  4. AFTER saving to auth DB → MUST create UserProfile in user-service                   │
│     Uses Feign Client to call user-service internally:                                  │
│                                                                                          │
│     @FeignClient(name = "user-service")                                                  │
│     public interface UserServiceClient {                                                 │
│         @PostMapping("/users/internal/create")                                           │
│         void createUserProfile(@RequestBody CreateUserInternalDTO dto);                 │
│     }                                                                                    │
│                                                                                          │
│     Feign resolves "user-service" via Eureka (Service Discovery)                        │
│     Actual URL becomes: http://user-service:8082/users/internal/create                  │
│                                                                                          │
│  5. Generates JWT token with claims:                                                     │
│     - sub: user_id (UUID from auth_credential)                                          │
│     - role: "MINING_AGENCY"                                                             │
│     - email: "contact@murdockmetals.com"                                                │
│     - exp: now + 24 hours                                                               │
│                                                                                          │
│  6. Publishes Kafka event to topic: agency.registered                                   │
│     KafkaProducer.send(new ProducerRecord("agency.registered", event))                  │
│                                                                                          │
│     Event payload:                                                                       │
│     {                                                                                    │
│       "userId": "550e8400-e29b-41d4-a716-446655440000",                                 │
│       "email": "contact@murdockmetals.com",                                              │
│       "role": "MINING_AGENCY",                                                           │
│       "timestamp": "2025-06-25T10:30:00"                                                │
│     }                                                                                    │
│                                                                                          │
│  7. Returns 201 Created response with JWT token to client                               │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
                    ▼               ▼               ▼
          ┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
          │  Feign Call to  │ │  Kafka Event    │ │  Response to    │
          │  user-service   │ │  to analytics   │ │  Client (JWT)   │
          └─────────────────┘ └─────────────────┘ └─────────────────┘
                    │               │
                    ▼               ▼

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            USER-SERVICE (port 8082)                                      │
│                                                                                          │
│  UserController.createUserProfile() receives Feign call:                                │
│                                                                                          │
│  1. Receives CreateUserInternalDTO:                                                      │
│     {                                                                                    │
│       "authUserId": "550e8400-e29b-41d4-a716-446655440000",                             │
│       "name": "Murdock Metals Ltd",                                                      │
│       "email": "contact@murdockmetals.com",                                              │
│       "role": "MINING_AGENCY"                                                            │
│     }                                                                                    │
│                                                                                          │
│  2. Saves UserProfile entity to MySQL (schema: nmts_users):                             │
│     INSERT INTO user_profile (id, auth_user_id, name, email, role, created_at)          │
│     VALUES (uuid, '550e8400-...', 'Murdock Metals Ltd',                                  │
│             'contact@murdockmetals.com', 'MINING_AGENCY', NOW())                        │
│                                                                                          │
│  3. Returns 200 OK (void response)                                                      │
│                                                                                          │
│  🎯 RESULT: Murdock Metals Ltd now has BOTH:                                             │
│     - AuthCredential in nmts_auth (for login)                                           │
│     - UserProfile in nmts_users (for profile management)                                │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         ANALYTICS-SERVICE (port 8089)                                    │
│                                                                                          │
│  Kafka Consumer for topic "agency.registered" wakes up:                                 │
│                                                                                          │
│  @KafkaListener(topics = "agency.registered")                                           │
│  public void consumeAgencyRegistered(AgencyRegisteredEvent event) {                     │
│                                                                                          │
│      1. Converts event to AgencyEventDocument:                                           │
│         AgencyEventDocument doc = new AgencyEventDocument();                             │
│         doc.setEventType("REGISTERED");                                                  │
│         doc.setAgencyId(event.getUserId());                                              │
│         doc.setEmail(event.getEmail());                                                  │
│         doc.setTimestamp(event.getTimestamp());                                          │
│         doc.setRawPayload(JSON.stringify(event));                                        │
│                                                                                          │
│      2. Saves to MongoDB (database: nmts_analytics, collection: agency_events):         │
│         mongoTemplate.save(doc);                                                         │
│                                                                                          │
│      3. Logs: "Agency registration event stored for analytics"                          │
│  }                                                                                       │
│                                                                                          │
│  🎯 RESULT: Analytics data point recorded for future dashboard metrics                  │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 2: MINING AGENCY LOGS IN

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 2: Murdock Metals Ltd logs in to the system                                        │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                              POST /auth/login
                           {
                             "email": "contact@murdockmetals.com",
                             "password": "SecurePass123!"
                           }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • /auth/login is in SKIP list → no JWT validation                                      │
│  • Routes to: lb://auth-service                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            AUTH-SERVICE (port 8081)                                      │
│                                                                                          │
│  AuthController.login() receives request:                                               │
│                                                                                          │
│  1. Finds AuthCredential by email:                                                      │
│     SELECT * FROM auth_credential WHERE email = 'contact@murdockmetals.com'            │
│     → Returns record for Murdock Metals Ltd                                             │
│                                                                                          │
│  2. BCrypt.checkpw("SecurePass123!", storedHash) → true                                 │
│                                                                                          │
│  3. Checks isActive flag → true (account not deactivated)                               │
│                                                                                          │
│  4. Generates NEW JWT token for this session                                            │
│                                                                                          │
│  5. Returns token to client                                                             │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 3: MINING AGENCY CREATES COMPANY PROFILE

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 3: Murdock Metals Ltd creates their detailed agency profile                        │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /agency/register
            Headers: Authorization: Bearer {JWT_TOKEN_FROM_LOGIN}
                    X-User-Id: 550e8400-e29b-41d4-a716-446655440000
                    X-User-Role: MINING_AGENCY
            Body: {
              "agencyName": "Murdock Metals Ltd",
              "agencyType": "PRIVATE",
              "registrationNumber": "MML-2024-001287",
              "location": "Accra, Ghana",
              "description": "Premium supplier of rare earth metals..."
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│                                                                                          │
│  • JwtAuthenticationFilter runs BEFORE routing:                                         │
│    1. Extracts Bearer token from Authorization header                                   │
│    2. Validates JWT signature using nmts.jwt.secret from config-server                 │
│    3. Decodes claims: sub, role, email                                                  │
│    4. Adds headers to request before forwarding:                                        │
│       - X-User-Id: 550e8400-e29b-41d4-a716-446655440000                                 │
│       - X-User-Role: MINING_AGENCY                                                      │
│       - X-User-Email: contact@murdockmetals.com                                         │
│    5. Routes to: lb://mining-agency-service                                             │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                       MINING-AGENCY-SERVICE (port 8083)                                  │
│                                                                                          │
│  AgencyController.registerAgency() receives request:                                    │
│                                                                                          │
│  1. RoleGuard checks: X-User-Role must be MINING_AGENCY                                 │
│     → Passed                                                                             │
│                                                                                          │
│  2. Extracts ownerId from X-User-Id header: "550e8400-e29b-41d4-a716-446655440000"     │
│                                                                                          │
│  3. Checks if ownerId already has an agency:                                             │
│     SELECT * FROM mining_agency WHERE owner_id = '550e8400-...'                         │
│     → No existing agency → proceed                                                      │
│                                                                                          │
│  4. Saves MiningAgency entity to MySQL (schema: nmts_agency):                           │
│     INSERT INTO mining_agency (id, owner_id, agency_name, agency_type,                  │
│                registration_number, location, description, operation_status,            │
│                created_at, updated_at)                                                  │
│     VALUES (uuid_generated(), '550e8400-...', 'Murdock Metals Ltd', 'PRIVATE',          │
│             'MML-2024-001287', 'Accra, Ghana', 'Premium supplier...',                   │
│             'ACTIVE', NOW(), NOW())                                                     │
│                                                                                          │
│  5. Returns 201 with agency details                                                     │
│                                                                                          │
│  🎯 RESULT: Murdock Metals Ltd is now a fully registered mining agency                  │
│     with operation_status = ACTIVE                                                       │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 4: LICENSE GRANTING (GOVERNMENT OFFICER ACTION)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 4: Captain Mensah (GOV_OFFICER) grants a 90-day license to Murdock Metals Ltd     │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /licenses/grant
            Headers: Authorization: Bearer {GOV_OFFICER_JWT}
                    X-User-Role: GOV_OFFICER
            Body: {
              "agencyId": "agency-company-uuid-here",
              "agencyName": "Murdock Metals Ltd",
              "licenseType": "DAY_90",
              "durationDays": 90
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Validates JWT, checks role = GOV_OFFICER                                             │
│  • Routes to: lb://license-service                                                      │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           LICENSE-SERVICE (port 8084)                                    │
│                                                                                          │
│  LicenseController.grantLicense() receives request:                                     │
│                                                                                          │
│  1. RoleGuard checks: GOV_OFFICER allowed                                                │
│                                                                                          │
│  2. Determines durationDays: DAY_90 → force 90 days                                     │
│                                                                                          │
│  3. Calculates expiresAt = LocalDateTime.now().plusDays(90)                             │
│                                                                                          │
│  4. Saves License entity to MySQL (schema: nmts_license):                               │
│     INSERT INTO license (id, agency_id, agency_name, license_type, duration_days,       │
│                issued_at, expires_at, status, issued_by_officer_id, created_at)         │
│     VALUES (uuid, 'agency-company-uuid', 'Murdock Metals Ltd', 'DAY_90', 90,            │
│             NOW(), NOW() + 90 days, 'ACTIVE', 'officer-uuid', NOW())                    │
│                                                                                          │
│  5. Publishes Kafka event to topic: license.granted                                     │
│     ProducerRecord("license.granted", event)                                            │
│                                                                                          │
│     Event: {                                                                             │
│       "licenseId": "license-uuid",                                                      │
│       "agencyId": "agency-company-uuid",                                                │
│       "licenseType": "DAY_90",                                                          │
│       "durationDays": 90,                                                               │
│       "issuedAt": "2025-06-25T11:00:00",                                                │
│       "expiresAt": "2025-09-23T11:00:00",                                               │
│       "officerId": "officer-uuid"                                                       │
│     }                                                                                    │
│                                                                                          │
│  6. Returns 201 with license details                                                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         ANALYTICS-SERVICE (port 8089)                                    │
│                                                                                          │
│  Kafka Consumer for "license.granted":                                                  │
│  • Converts event to LicenseEventDocument with type="GRANTED"                           │
│  • Saves to MongoDB (license_events collection)                                         │
│  • This data will appear in admin dashboard license activity charts                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 5: MINING AGENCY CREATES METAL LISTING

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 5: Murdock Metals Ltd creates a Lithium listing (requires ACTIVE license)         │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /agency/listings
            Headers: Authorization: Bearer {AGENCY_JWT}
                    X-User-Id: 550e8400-...
                    X-User-Role: MINING_AGENCY
            Body: {
              "metalName": "Lithium",
              "metalCategory": "EARTH_METAL",
              "pricePerTon": 75000.00,
              "availableQtyTons": 500.00
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Validates JWT, checks role = MINING_AGENCY                                           │
│  • Routes to: lb://mining-agency-service                                                │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                       MINING-AGENCY-SERVICE (port 8083)                                  │
│                                                                                          │
│  ListingController.createListing() receives request:                                    │
│                                                                                          │
│  1. Finds agency by ownerId (X-User-Id):                                                │
│     SELECT * FROM mining_agency WHERE owner_id = '550e8400-...'                         │
│     → Returns Murdock Metals Ltd agency record                                          │
│                                                                                          │
│  2. Checks agency.operationStatus = ACTIVE → proceed                                    │
│                                                                                          │
│  3. **CRITICAL STEP: Verifies agency has ACTIVE license**                               │
│     Calls LicenseServiceClient via Feign:                                                │
│                                                                                          │
│     @FeignClient(name = "license-service")                                               │
│     public interface LicenseServiceClient {                                              │
│         @GetMapping("/licenses/agency/{agencyId}/status")                               │
│         LicenseStatusDTO getAgencyLicenseStatus(@PathVariable UUID agencyId);          │
│     }                                                                                    │
│                                                                                          │
│     Feign call: GET http://license-service:8084/licenses/agency/{agencyId}/status       │
│     Response: { hasActiveLicense: true, licenseId: "xxx", expiresAt: "2025-09-23" }    │
│                                                                                          │
│     If hasActiveLicense = false → return 403 "Agency must hold active license"         │
│                                                                                          │
│  4. Saves MetalListing to MySQL (nmts_agency):                                          │
│     INSERT INTO metal_listing (id, agency_id, metal_name, metal_category,               │
│                price_per_ton, available_qty_tons, is_active, created_at)                │
│     VALUES (uuid, 'agency-uuid', 'Lithium', 'EARTH_METAL', 75000.00, 500.00,            │
│             true, NOW())                                                                │
│                                                                                          │
│  5. Publishes Kafka event to topic: listing.created                                     │
│     ProducerRecord("listing.created", event)                                            │
│                                                                                          │
│     Event: {                                                                             │
│       "listingId": "listing-uuid",                                                      │
│       "agencyId": "agency-uuid",                                                        │
│       "agencyName": "Murdock Metals Ltd",                                               │
│       "metalName": "Lithium",                                                           │
│       "pricePerTon": 75000.00,                                                          │
│       "availableQtyTons": 500.00                                                        │
│     }                                                                                    │
│                                                                                          │
│  6. Returns 201 with listing details                                                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    │                               │
                    ▼                               ▼
┌─────────────────────────────────┐   ┌─────────────────────────────────┐
│   SEARCH-CATALOG-SERVICE        │   │   ANALYTICS-SERVICE             │
│        (port 8087)              │   │        (port 8089)              │
│                                 │   │                                 │
│  Consumer: listing.created      │   │  Consumer: listing.created      │
│                                 │   │                                 │
│  Calls: evictCache()            │   │  Saves to MongoDB:              │
│  @CacheEvict(allEntries=true)   │   │  listing_events collection      │
│                                 │   │                                 │
│  🎯 Why evict cache?            │   │  🎯 Why save to MongoDB?         │
│  Because new listing available  │   │  For analytics dashboard        │
│  Next search must show it       │   │  (total listings count)         │
└─────────────────────────────────┘   └─────────────────────────────────┘
```

---

## 🔄 FLOW 6: CUSTOMER REGISTRATION & PROFILE

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 6: Chen Electronics Inc registers as CUSTOMER                                      │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /auth/register
                           {
                             "name": "Chen Electronics Inc",
                             "email": "procurement@chenelec.com",
                             "password": "CustomerPass789!",
                             "role": "CUSTOMER"
                           }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Routes to: lb://auth-service                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            AUTH-SERVICE (port 8081)                                      │
│                                                                                          │
│  1. Saves AuthCredential for Chen Electronics Inc (role = CUSTOMER)                     │
│                                                                                          │
│  2. Calls user-service via Feign to create UserProfile                                  │
│                                                                                          │
│  3. ⚠️ NO Kafka event published (only MINING_AGENCY registration triggers event)       │
│                                                                                          │
│  4. Returns JWT token for Chen Electronics Inc                                          │
└─────────────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 6b: Chen Electronics Inc updates profile (adds phone, address)                     │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           PUT /users/profile
            Headers: Authorization: Bearer {CUSTOMER_JWT}
            Body: {
              "phone": "+852-9123-4567",
              "address": "Unit 45, 88 Electric Road, Hong Kong",
              "businessName": "Chen Electronics Manufacturing Ltd"
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Routes to: lb://user-service                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                            USER-SERVICE (port 8082)                                      │
│                                                                                          │
│  UserController.updateProfile() receives request:                                       │
│                                                                                          │
│  1. Extracts X-User-Id from header                                                      │
│                                                                                          │
│  2. Updates UserProfile in nmts_users:                                                  │
│     UPDATE user_profile SET phone = '+852-9123-4567',                                   │
│            address = 'Unit 45...', business_name = 'Chen Electronics...'                │
│     WHERE auth_user_id = 'customer-uuid'                                                │
│                                                                                          │
│  3. Returns updated profile                                                             │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 7: CUSTOMER SEARCHES FOR METALS (WITH REDIS CACHING)

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 7: Chen Electronics Inc searches for Lithium suppliers                             │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                    GET /search/metals/by-name?metalName=Lithium
            Headers: Authorization: Bearer {CUSTOMER_JWT}
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Validates JWT                                                                         │
│  • Routes to: lb://search-catalog-service                                               │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                      SEARCH-CATALOG-SERVICE (port 8087)                                  │
│                                                                                          │
│  SearchController.searchByMetalName() receives request:                                 │
│                                                                                          │
│  1. @Cacheable(value="metalSearch", key="#metalName.toLowerCase().trim()")              │
│     → Checks Redis cache for key "lithium"                                              │
│     → First search: CACHE MISS → proceed to database                                    │
│                                                                                          │
│  2. Calls MiningAgencyServiceClient via Feign:                                          │
│     @FeignClient(name = "mining-agency-service")                                        │
│     GET /agency/listings/search?metalName=Lithium                                       │
│                                                                                          │
│     This Feign call goes to mining-agency-service:                                      │
│     SELECT * FROM metal_listing WHERE metal_name LIKE '%Lithium%'                       │
│     AND is_active = true AND agency_id IN (                                            │
│         SELECT id FROM mining_agency WHERE operation_status = 'ACTIVE'                  │
│     )                                                                                    │
│     → Returns listing from Murdock Metals Ltd                                            │
│                                                                                          │
│  3. For each unique agencyId in results, calls LicenseServiceClient via Feign:          │
│     GET /licenses/agency/{agencyId}/status                                              │
│                                                                                          │
│     Checks if agency has ACTIVE and non-expired license                                 │
│     → Murdock Metals Ltd has ACTIVE license → include in results                        │
│                                                                                          │
│  4. Filters results: only agencies with hasActiveLicense = true                         │
│                                                                                          │
│  5. Stores result in Redis:                                                              │
│     redisTemplate.opsForValue().set("lithium", searchResults, 10, TimeUnit.MINUTES)    │
│                                                                                          │
│  6. Returns search results to customer                                                  │
│                                                                                          │
│  🎯 SUBSEQUENT SEARCHES for "Lithium" will be CACHE HITS                                 │
│     (no Feign calls, no database queries, ~10x faster)                                  │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 8: CUSTOMER SENDS PURCHASE REQUEST

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 8: Chen Electronics Inc requests 50 tons of Lithium from Murdock Metals Ltd       │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                      POST /agency/purchase-requests
            Headers: Authorization: Bearer {CUSTOMER_JWT}
                    X-User-Id: customer-uuid
            Body: {
              "listingId": "listing-uuid-from-search",
              "requestedQtyTons": 50.00,
              "message": "Required for battery production"
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              API GATEWAY (port 8080)                                     │
│  • Validates JWT (role = CUSTOMER)                                                      │
│  • Routes to: lb://mining-agency-service                                                │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                       MINING-AGENCY-SERVICE (port 8083)                                  │
│                                                                                          │
│  PurchaseRequestController.createRequest() receives request:                           │
│                                                                                          │
│  1. Finds listing by listingId:                                                         │
│     SELECT * FROM metal_listing WHERE id = 'listing-uuid' AND is_active = true         │
│                                                                                          │
│  2. Finds agency by agencyId from listing:                                              │
│     SELECT operation_status FROM mining_agency WHERE id = 'agency-uuid'                │
│     → Must be ACTIVE                                                                    │
│                                                                                          │
│  3. Verifies agency has ACTIVE license (calls LicenseServiceClient via Feign)          │
│                                                                                          │
│  4. Calculates totalEstimatedValue = 50.00 × 75000.00 = 3,750,000.00                    │
│                                                                                          │
│  5. Saves PurchaseRequest to MySQL (nmts_agency):                                       │
│     INSERT INTO purchase_request (id, listing_id, agency_id, customer_id,               │
│                customer_name, requested_qty_tons, price_per_ton,                        │
│                total_estimated_value, message, status, created_at)                      │
│     VALUES (uuid, 'listing-uuid', 'agency-uuid', 'customer-uuid',                       │
│             'Chen Electronics Inc', 50.00, 75000.00, 3750000.00,                        │
│             'Required for battery...', 'PENDING', NOW())                                │
│                                                                                          │
│  6. Publishes Kafka event to topic: purchase.requested                                  │
│     ProducerRecord("purchase.requested", event)                                         │
│                                                                                          │
│     Event: {                                                                             │
│       "requestId": "request-uuid",                                                      │
│       "customerId": "customer-uuid",                                                    │
│       "customerName": "Chen Electronics Inc",                                           │
│       "agencyId": "agency-uuid",                                                        │
│       "metalName": "Lithium",                                                           │
│       "requestedQtyTons": 50.00,                                                        │
│       "totalEstimatedValue": 3750000.00                                                 │
│     }                                                                                    │
│                                                                                          │
│  7. Returns 201 with request details                                                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         ANALYTICS-SERVICE (port 8089)                                    │
│                                                                                          │
│  Consumer for "purchase.requested":                                                     │
│  • Saves to MongoDB (purchase_events collection with type="REQUESTED")                 │
│  • Used for purchase trends in admin dashboard                                          │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 9: MINING AGENCY APPROVES PURCHASE REQUEST

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 9: Murdock Metals Ltd approves Chen Electronics Inc's purchase request            │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
          PATCH /agency/purchase-requests/{requestId}/approve
            Headers: Authorization: Bearer {AGENCY_JWT}
                    X-User-Id: agency-owner-uuid
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                       MINING-AGENCY-SERVICE (port 8083)                                  │
│                                                                                          │
│  PurchaseRequestController.approveRequest() receives request:                          │
│                                                                                          │
│  1. Finds PurchaseRequest by ID                                                         │
│                                                                                          │
│  2. Verifies the request belongs to this agency:                                        │
│     agency_id from purchase_request must match agency_id from this agency              │
│                                                                                          │
│  3. Checks current status = PENDING → proceed                                           │
│                                                                                          │
│  4. Updates status to APPROVED:                                                         │
│     UPDATE purchase_request SET status = 'APPROVED', updated_at = NOW()                 │
│     WHERE id = 'request-uuid'                                                           │
│                                                                                          │
│  5. Publishes Kafka event to topic: purchase.approved                                   │
│     ProducerRecord("purchase.approved", event)                                          │
│                                                                                          │
│     Event: {                                                                             │
│       "requestId": "request-uuid",                                                      │
│       "customerId": "customer-uuid",                                                    │
│       "agencyId": "agency-uuid",                                                        │
│       "metalName": "Lithium",                                                           │
│       "requestedQtyTons": 50.00,                                                        │
│       "totalEstimatedValue": 3750000.00,                                                │
│       "approvedAt": "2025-06-25T15:45:00"                                               │
│     }                                                                                    │
│                                                                                          │
│  6. Returns 200 with approval confirmation                                              │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                    ┌───────────────┴───────────────┐
                    │                               │
                    ▼                               ▼
┌─────────────────────────────────┐   ┌─────────────────────────────────┐
│         USER-SERVICE            │   │      ANALYTICS-SERVICE          │
│          (port 8082)            │   │          (port 8089)            │
│                                 │   │                                 │
│  Consumer: purchase.approved    │   │  Consumer: purchase.approved    │
│                                 │   │                                 │
│  Saves to PurchaseHistory:      │   │  Saves to MongoDB:              │
│  INSERT INTO purchase_history   │   │  purchase_events collection     │
│  (customer_id, agency_id,       │   │  with type="APPROVED"           │
│   metal_name, requested_qty,    │   │                                 │
│   price_per_ton, total_value,   │   │                                 │
│   status, processed_at)         │   │                                 │
│                                 │   │                                 │
│  🎯 Customer can now view        │   │  🎯 Used for:                   │
│  this in their purchase history │   │  - Approved purchases count     │
│                                 │   │  - Revenue analytics            │
└─────────────────────────────────┘   └─────────────────────────────────┘
```

---

## 🔄 FLOW 10: COMPLIANCE REPORT FILING

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 10: Emma Williams (REPORTER) files a report against Murdock Metals Ltd            │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /reports
            Headers: Authorization: Bearer {REPORTER_JWT}
                    X-User-Id: reporter-uuid
            Body: {
              "agencyId": "agency-uuid",
              "agencyName": "Murdock Metals Ltd",
              "title": "Environmental violations detected",
              "description": "Satellite imagery shows waste dumping",
              "severity": "HIGH"
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                           REPORT-SERVICE (port 8085)                                     │
│                                                                                          │
│  ReportController.fileReport() receives request:                                        │
│                                                                                          │
│  1. Saves ComplianceReport to MySQL (nmts_report):                                      │
│     INSERT INTO compliance_report (id, reporter_id, reporter_name, agency_id,           │
│                agency_name, title, description, severity, status, created_at)           │
│     VALUES (uuid, 'reporter-uuid', 'Emma Williams', 'agency-uuid',                      │
│             'Murdock Metals Ltd', 'Environmental...', 'Satellite...',                   │
│             'HIGH', 'PENDING', NOW())                                                   │
│                                                                                          │
│  2. Publishes Kafka event to topic: report.filed                                        │
│     ProducerRecord("report.filed", event)                                               │
│                                                                                          │
│     Event: {                                                                             │
│       "reportId": "report-uuid",                                                        │
│       "reporterId": "reporter-uuid",                                                    │
│       "agencyId": "agency-uuid",                                                        │
│       "severity": "HIGH"                                                                │
│     }                                                                                    │
│                                                                                          │
│  3. Returns 201                                                                         │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         ANALYTICS-SERVICE (port 8089)                                    │
│                                                                                          │
│  Consumer for "report.filed":                                                           │
│  • Saves to MongoDB (report_events collection)                                          │
│  • Used for reports-by-severity analytics                                               │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 11: GOVERNMENT OFFICER ISSUES SEIZURE ORDER

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 11: Captain Mensah issues seizure order against Murdock Metals Ltd                │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           POST /government/seizures
            Headers: Authorization: Bearer {GOV_OFFICER_JWT}
            Body: {
              "reportId": "report-uuid",
              "agencyId": "agency-uuid",
              "agencyName": "Murdock Metals Ltd",
              "reason": "Confirmed environmental violations"
            }
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         GOVERNMENT-SERVICE (port 8086)                                   │
│                                                                                          │
│  GovernmentController.issueSeizure() receives request:                                  │
│                                                                                          │
│  1. Saves SeizureOrder to MySQL (nmts_government):                                      │
│     INSERT INTO seizure_order (id, officer_id, report_id, agency_id,                    │
│                agency_name, reason, issued_at, created_at)                              │
│     VALUES (uuid, 'officer-uuid', 'report-uuid', 'agency-uuid',                         │
│             'Murdock Metals Ltd', 'Confirmed...', NOW(), NOW())                         │
│                                                                                          │
│  2. Calls MiningAgencyServiceClient via Feign to update agency status:                  │
│     PATCH http://mining-agency-service:8083/agency/{agencyId}/status                    │
│     Body: { "operationStatus": "SEIZED" }                                               │
│                                                                                          │
│     mining-agency-service receives this and updates:                                    │
│     UPDATE mining_agency SET operation_status = 'SEIZED' WHERE id = 'agency-uuid'      │
│                                                                                          │
│  3. Calls ReportServiceClient via Feign to update report status:                        │
│     PATCH http://report-service:8085/reports/internal/{reportId}/status                 │
│     Body: { "status": "ACTION_TAKEN" }                                                  │
│                                                                                          │
│  4. Publishes Kafka event to topic: operation.seized                                    │
│     ProducerRecord("operation.seized", event)                                           │
│                                                                                          │
│     Event: {                                                                             │
│       "seizureOrderId": "seizure-uuid",                                                 │
│       "agencyId": "agency-uuid",                                                        │
│       "officerId": "officer-uuid",                                                      │
│       "reason": "Confirmed environmental violations",                                   │
│       "issuedAt": "2025-06-26T11:30:00"                                                 │
│     }                                                                                    │
│                                                                                          │
│  5. Returns 201 with seizure details                                                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                    MULTIPLE KAFKA CONSUMERS REACT TO operation.seized                    │
│                                                                                          │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │ MINING-AGENCY-SERVICE Consumer:                                                  │   │
│  │ • Listens to operation.seized                                                    │   │
│  │ • Updates agency.operation_status = SEIZED (redundant but ensures consistency)  │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                          │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │ SEARCH-CATALOG-SERVICE Consumer:                                                 │   │
│  │ • Listens to operation.seized                                                    │   │
│  • Calls evictCache() → removes all cached search results                           │   │
│  │ • 🎯 EFFECT: Seized agency's listings NO LONGER appear in search results        │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
│                                                                                          │
│  ┌─────────────────────────────────────────────────────────────────────────────────┐   │
│  │ ANALYTICS-SERVICE Consumer:                                                      │   │
│  │ • Saves to MongoDB (seizure_events and agency_events with type="SEIZED")        │   │
│  │ • Used for dashboard metrics (seized agencies count)                             │   │
│  └─────────────────────────────────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🔄 FLOW 12: ADMIN VIEWS ANALYTICS DASHBOARD

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│ STEP 12: David Okafor (ADMIN) views platform analytics                                  │
└─────────────────────────────────────────────────────────────────────────────────────────┘

                                    HTTP REQUEST
                           GET /analytics/summary
            Headers: Authorization: Bearer {ADMIN_JWT}
                                    │
                                    ▼
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                         ANALYTICS-SERVICE (port 8089)                                    │
│                                                                                          │
│  AnalyticsController.getPlatformSummary() receives request:                             │
│                                                                                          │
│  1. Counts from MongoDB (event streams):                                                │
│     - totalAgencies = db.agency_events.countDocuments()                                 │
│     - seizedAgencies = db.agency_events.countDocuments({eventType: "SEIZED"})          │
│     - totalListings = db.listing_events.countDocuments()                                │
│     - totalPurchaseRequests = db.purchase_events.countDocuments()                       │
│     - approvedPurchases = db.purchase_events.countDocuments({eventType: "APPROVED"})   │
│     - rejectedPurchases = db.purchase_events.countDocuments({eventType: "REJECTED"})   │
│                                                                                          │
│  2. Calls Feign clients for real-time snapshot data:                                    │
│     - activeAgencies = MiningAgencyServiceClient.getAllAgencies()                       │
│                          .filter(status = ACTIVE).count()                               │
│     - activeListings = MiningAgencyServiceClient.getAllListings()                       │
│                         .filter(isActive = true).count()                                │
│     - activeLicenses = LicenseServiceClient.getAllLicenses()                            │
│                         .filter(status = ACTIVE).count()                                │
│                                                                                          │
│  3. Aggregates and returns PlatformSummaryDTO:                                          │
│     {                                                                                    │
│       "totalAgencies": 47,                                                              │
│       "activeAgencies": 32,                                                             │
│       "seizedAgencies": 3,                                                              │
│       "totalListings": 156,                                                             │
│       "activeListings": 128,                                                            │
│       "totalPurchaseRequests": 892,                                                     │
│       "approvedPurchases": 645,                                                         │
│       "totalLicenses": 89,                                                              │
│       "activeLicenses": 67                                                              │
│     }                                                                                    │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 📊 COMPLETE SYSTEM INTERACTION SUMMARY

```
┌─────────────────────────────────────────────────────────────────────────────────────────┐
│                              ALL SERVICES INTERACTING                                   │
├─────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                          │
│  REGISTRATION FLOW:                                                                      │
│  Client → Gateway → auth-service → [Feign→user-service] → [Kafka→analytics-service]    │
│                                                                                          │
│  LISTING CREATION FLOW:                                                                  │
│  Client → Gateway → mining-agency-service → [Feign→license-service]                     │
│                                          → [Kafka→search-catalog-service]               │
│                                          → [Kafka→analytics-service]                    │
│                                                                                          │
│  SEARCH FLOW:                                                                            │
│  Client → Gateway → search-catalog-service → [Feign→mining-agency-service]              │
│                                            → [Feign→license-service]                    │
│                                            → [Redis Cache]                              │
│                                                                                          │
│  PURCHASE FLOW:                                                                          │
│  Client → Gateway → mining-agency-service → [Kafka→user-service]                        │
│                                            → [Kafka→analytics-service]                  │
│                                                                                          │
│  SEIZURE FLOW:                                                                           │
│  Client → Gateway → government-service → [Feign→mining-agency-service]                  │
│                                        → [Feign→report-service]                         │
│                                        → [Kafka→mining-agency-service]                  │
│                                        → [Kafka→search-catalog-service]                 │
│                                        → [Kafka→analytics-service]                      │
│                                                                                          │
└─────────────────────────────────────────────────────────────────────────────────────────┘
```

---

## 🗄️ DATABASE SCHEMAS PER SERVICE

| Service | Database | Tables/Collections |
|---------|----------|-------------------|
| auth-service | MySQL: nmts_auth | auth_credential |
| user-service | MySQL: nmts_users | user_profile, purchase_history |
| mining-agency-service | MySQL: nmts_agency | mining_agency, metal_listing, purchase_request |
| license-service | MySQL: nmts_license | license |
| report-service | MySQL: nmts_report | compliance_report |
| government-service | MySQL: nmts_government | seizure_order |
| search-catalog-service | MySQL: nmts_catalog + Redis | metal_catalog (MySQL) + cache (Redis) |
| analytics-service | MongoDB: nmts_analytics | agency_events, listing_events, purchase_events, license_events, report_events, seizure_events |

---

## 📋 KAFKA TOPICS SUMMARY

| Topic | Producer | Consumer(s) | Purpose |
|-------|----------|-------------|---------|
| agency.registered | auth-service | analytics-service | Track new agency registrations |
| listing.created | mining-agency-service | search-catalog, analytics | Update cache + track listings |
| purchase.requested | mining-agency-service | analytics-service | Track purchase requests |
| purchase.approved | mining-agency-service | user-service, analytics | Save history + track approvals |
| purchase.rejected | mining-agency-service | user-service, analytics | Save history + track rejections |
| license.granted | license-service | analytics-service | Track license issuance |
| license.revoked | license-service | search-catalog, analytics | Evict cache + track revocations |
| report.filed | report-service | analytics-service | Track compliance reports |
| operation.seized | government-service | mining-agency, search-catalog, analytics | Update status, evict cache, track seizures |