# Manual Test Cases — Login → Create Lead → List Lead

**Application URL:** https://v0-lead-manager-app.vercel.app  
**Test Credentials:**

| Role | Email | Password | Access |
|------|-------|----------|--------|
| Admin | admin@company.com | Admin@123 | Full access: Create, Edit, Delete, View, Export |
| Manager | qa@company.com | password123 | Limited: Create, Edit, View, Export (No Delete) |
| Viewer | tester@company.com | Test@456 | Read-only: View only |

---

## Module 1: Login

### TC-L01: Login with valid Admin credentials
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `admin@company.com`
  2. Enter password: `Admin@123`
  3. Click "Sign in"
- **Expected Result:** User is redirected to the dashboard. Header shows `admin@company.com` with `Administrator` badge. Leads table is displayed.

### TC-L02: Login with valid Manager credentials
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `qa@company.com`
  2. Enter password: `password123`
  3. Click "Sign in"
- **Expected Result:** User is redirected to the dashboard with `Manager` badge. Delete action (trash icon) is not visible on lead rows.

### TC-L03: Login with valid Viewer credentials
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `tester@company.com`
  2. Enter password: `Test@456`
  3. Click "Sign in"
- **Expected Result:** User is redirected to the dashboard with `Viewer` badge. "Create Lead" button, Edit (pencil) icon, and Delete (trash) icon are not visible.

### TC-L04: Login with wrong password
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `admin@company.com`
  2. Enter password: `wrongpass`
  3. Click "Sign in"
- **Expected Result:** Login fails. Error message "Invalid credentials" is displayed. User remains on the login page.

### TC-L05: Login with unregistered email
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `unknown@test.com`
  2. Enter password: `password123`
  3. Click "Sign in"
- **Expected Result:** Login fails. Error message is displayed. User remains on the login page.

### TC-L06: Login with invalid email format
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `notanemail`
  2. Enter password: `password123`
  3. Click "Sign in"
- **Expected Result:** Browser or application shows email validation error. Login is not attempted.

### TC-L07: Login with empty email and password
- **Preconditions:** User is on the login page
- **Steps:**
  1. Leave email field empty
  2. Leave password field empty
  3. Click "Sign in"
- **Expected Result:** Validation errors are shown for both fields. Login is not attempted.

### TC-L08: Login with empty email only
- **Preconditions:** User is on the login page
- **Steps:**
  1. Leave email field empty
  2. Enter password: `Admin@123`
  3. Click "Sign in"
- **Expected Result:** Validation error is shown for the email field. Login is not attempted.

### TC-L09: Login with empty password only
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `admin@company.com`
  2. Leave password field empty
  3. Click "Sign in"
- **Expected Result:** Validation error is shown for the password field. Login is not attempted.

### TC-L10: Login — password field masks input
- **Preconditions:** User is on the login page
- **Steps:**
  1. Click on the password field
  2. Type `Admin@123`
- **Expected Result:** Characters are masked (shown as dots/bullets). Input type is `password`.

### TC-L11: Access dashboard URL without login
- **Preconditions:** User is not logged in (clear cookies/session)
- **Steps:**
  1. Navigate directly to `https://v0-lead-manager-app.vercel.app/dashboard/leads`
- **Expected Result:** User is redirected to the login page. Dashboard is not accessible.

### TC-L12: Login with SQL injection in email field
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `' OR 1=1 --`
  2. Enter password: `anything`
  3. Click "Sign in"
- **Expected Result:** Login fails with an error message. No unauthorized access is granted.

### TC-L13: Login with leading/trailing spaces in email
- **Preconditions:** User is on the login page
- **Steps:**
  1. Enter email: `  admin@company.com  `
  2. Enter password: `Admin@123`
  3. Click "Sign in"
- **Expected Result:** Application trims spaces and logs in successfully, OR shows a clear validation error.

### TC-L14: Logout and verify session is cleared
- **Preconditions:** User is logged in as Admin
- **Steps:**
  1. Click "Logout" button in the header
  2. Press browser back button
- **Expected Result:** User is redirected to the login page. Dashboard is not accessible via back button.

---

## Module 2: Create Lead

### TC-C01: Create lead with all mandatory fields (Name, Email)
- **Preconditions:** User is logged in as Admin. Dashboard is displayed.
- **Steps:**
  1. Click "+ Create Lead" button
  2. Enter Name: `John Doe`
  3. Enter Email: `john.doe@test.com`
  4. Leave all other fields as default
  5. Click "Create Lead"
- **Expected Result:** Lead is created successfully. Success message/toast is displayed. Modal closes. New lead appears in the leads list.

### TC-C02: Create lead with all fields filled
- **Preconditions:** User is logged in as Admin. Dashboard is displayed.
- **Steps:**
  1. Click "+ Create Lead"
  2. Enter Name: `Jane Smith`
  3. Enter Email: `jane.smith@corp.com`
  4. Enter Phone: `+1 (555) 999-8888`
  5. Enter Company: `Tech Corp`
  6. Enter Job Title: `VP of Sales`
  7. Select Industry: `Technology`
  8. Select Source: `LinkedIn`
  9. Select Priority: `High`
  10. Select Status: `New`
  11. Enter Deal Value: `75000`
  12. Set Expected Close Date: a future date
  13. Set Follow-up Date: a future date
  14. Check "Lead is qualified"
  15. Check "Opted in for email communications"
  16. Enter Notes: `Interested in enterprise plan`
  17. Click "Create Lead"
- **Expected Result:** Lead is created with all fields populated. Lead appears in the list with correct Priority (`High`), Status (`New`), Deal Value (`$75,000`), and Source (`LinkedIn`).

### TC-C03: Create lead with only Name (missing mandatory Email)
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Test Lead`
  2. Leave Email field empty
  3. Click "Create Lead"
- **Expected Result:** Validation error is shown for the Email field. Lead is not created.

### TC-C04: Create lead with only Email (missing mandatory Name)
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Leave Name field empty
  2. Enter Email: `test@test.com`
  3. Click "Create Lead"
- **Expected Result:** Validation error is shown for the Name field. Lead is not created.

### TC-C05: Create lead with invalid email format
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Invalid Email Lead`
  2. Enter Email: `not-an-email`
  3. Click "Create Lead"
- **Expected Result:** Validation error is shown for the Email field. Lead is not created.

### TC-C06: Create lead with duplicate email
- **Preconditions:** User is logged in as Admin. A lead with email `brian.young@ecommerce.co` already exists.
- **Steps:**
  1. Click "+ Create Lead"
  2. Enter Name: `Duplicate Test`
  3. Enter Email: `brian.young@ecommerce.co`
  4. Click "Create Lead"
- **Expected Result:** Either a duplicate warning is shown, or the lead is created (depending on business rules). Verify expected behavior.

### TC-C07: Create lead with each Priority level
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Create a lead with Priority: `Low`
  2. Create a lead with Priority: `Medium`
  3. Create a lead with Priority: `High`
  4. Create a lead with Priority: `Critical`
- **Expected Result:** Each lead is created successfully. Priority badge color matches the selected level in the leads list (Low, Medium=orange, High=red, Critical=dark red).

### TC-C08: Create lead with each Status value
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Create a lead with Status: `New`
  2. Create a lead with Status: `Contacted`
  3. Create a lead with Status: `Qualified`
- **Expected Result:** Each lead is created successfully. Status badge in the list reflects the selected value.

### TC-C09: Create lead with negative Deal Value
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Negative Deal`
  2. Enter Email: `neg@test.com`
  3. Enter Deal Value: `-5000`
  4. Click "Create Lead"
- **Expected Result:** Validation error is shown for Deal Value, OR the field rejects negative input.

### TC-C10: Create lead with zero Deal Value
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Zero Deal`
  2. Enter Email: `zero@test.com`
  3. Enter Deal Value: `0`
  4. Click "Create Lead"
- **Expected Result:** Lead is created successfully with Deal Value showing `$0`.

### TC-C11: Create lead with very long Name (boundary test)
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: 256+ character string
  2. Enter Email: `long@test.com`
  3. Click "Create Lead"
- **Expected Result:** Either the field truncates input to max allowed length, or a validation error is shown.

### TC-C12: Create lead with special characters in Name
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `O'Brien-Smith & Co. <script>alert('xss')</script>`
  2. Enter Email: `special@test.com`
  3. Click "Create Lead"
- **Expected Result:** Lead is created with the name displayed correctly (HTML-escaped). No XSS script execution occurs.

### TC-C13: Create lead with past Expected Close Date
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Fill mandatory fields (Name, Email)
  2. Set Expected Close Date to a past date (e.g., 01/01/2020)
  3. Click "Create Lead"
- **Expected Result:** Validation error is shown indicating the date must be in the future, OR the lead is created (verify business rules).

### TC-C14: Cancel lead creation
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Cancel Test`
  2. Enter Email: `cancel@test.com`
  3. Click "Cancel" button
- **Expected Result:** Modal closes. No new lead is created. Dashboard list remains unchanged.

### TC-C15: Close Create Lead modal using X button
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Close Test`
  2. Click the X (close) button on the modal
- **Expected Result:** Modal closes without saving. No new lead is created.

### TC-C16: Create lead as Viewer role (permission test)
- **Preconditions:** User is logged in as Viewer (`tester@company.com`).
- **Steps:**
  1. Verify if "+ Create Lead" button is visible
  2. If visible, attempt to click it
- **Expected Result:** "Create Lead" button is not visible or is disabled for Viewer role. Lead creation is not possible.

### TC-C17: Create lead with phone number in invalid format
- **Preconditions:** User is logged in as Admin. Create Lead modal is open.
- **Steps:**
  1. Enter Name: `Phone Test`
  2. Enter Email: `phone@test.com`
  3. Enter Phone: `abc-not-a-number`
  4. Click "Create Lead"
- **Expected Result:** Validation error for phone field, OR the field accepts freeform text (verify business rules).

---

## Module 3: List Leads

### TC-LL01: Verify leads list displays after login
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Observe the dashboard page
- **Expected Result:** Leads table is displayed with columns: ID, Name, Email, Company, Priority, Status, Deal Value, Source, Created, Actions. Table shows "Showing 1 to 10 of X leads".

### TC-LL02: Verify leads table columns and data
- **Preconditions:** User is logged in as Admin. Leads exist in the system.
- **Steps:**
  1. Observe the first row in the leads table
- **Expected Result:** Each lead row displays: ID (numeric), Name, Email, Company, Priority (color-coded badge), Status (color-coded badge), Deal Value ($ formatted), Source, Created date, and Action icons (view, edit, delete).

### TC-LL03: Verify pagination — navigate to next page
- **Preconditions:** User is logged in as Admin. Total leads > 10.
- **Steps:**
  1. Verify "Showing 1 to 10 of X leads" is displayed
  2. Click "Next" button
- **Expected Result:** Page 2 loads with the next set of leads. "Previous" button becomes active. Lead rows are different from page 1.

### TC-LL04: Verify pagination — navigate to previous page
- **Preconditions:** User is on page 2 of the leads list.
- **Steps:**
  1. Click "Previous" button
- **Expected Result:** Page 1 loads with the original set of leads. "Previous" button is disabled on page 1.

### TC-LL05: Verify "Previous" button is disabled on first page
- **Preconditions:** User is logged in as Admin. On page 1 of leads list.
- **Steps:**
  1. Observe the "Previous" button
- **Expected Result:** "Previous" button is disabled/greyed out and not clickable.

### TC-LL06: Verify "Next" button is disabled on last page
- **Preconditions:** User is logged in as Admin. Navigate to the last page of leads.
- **Steps:**
  1. Click "Next" until the last page
  2. Observe the "Next" button
- **Expected Result:** "Next" button is disabled/greyed out on the last page.

### TC-LL07: Search leads by name
- **Preconditions:** User is logged in as Admin. Leads exist with name "Sarah Johnson".
- **Steps:**
  1. Click on the search field ("Search by name, email, or phone...")
  2. Type `Sarah`
- **Expected Result:** Leads list filters to show only leads matching "Sarah". Other leads are hidden.

### TC-LL08: Search leads by email
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Type `techcorp` in the search field
- **Expected Result:** Leads list filters to show leads with email containing "techcorp".

### TC-LL09: Search leads by phone number
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Type `555-123` in the search field
- **Expected Result:** Leads list filters to show leads with matching phone numbers.

### TC-LL10: Search with no matching results
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Type `zzzznonexistent` in the search field
- **Expected Result:** Table shows empty state or "No leads found" message. No leads are displayed.

### TC-LL11: Filter leads by Status
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Click the "All Statuses" dropdown
  2. Select "Contacted"
- **Expected Result:** Only leads with Status "Contacted" are displayed (e.g., Jennifer Moore, Michael Chen, Nicole Walker).

### TC-LL12: Clear search and verify all leads return
- **Preconditions:** User has an active search filter applied.
- **Steps:**
  1. Clear the search field
- **Expected Result:** Full leads list is restored. Pagination shows original total count.

### TC-LL13: Sort leads by column (Name)
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Click on the "Name" column header (sort icon ↑↓)
- **Expected Result:** Leads are sorted alphabetically by Name. Clicking again reverses the sort order.

### TC-LL14: Sort leads by Deal Value
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Click on the "Deal Value" column header
- **Expected Result:** Leads are sorted by Deal Value (ascending or descending). Clicking again reverses the order.

### TC-LL15: Verify newly created lead appears in the list
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Note the current total lead count
  2. Create a new lead with Name: `Verification Lead`, Email: `verify@test.com`
  3. Observe the leads list
- **Expected Result:** Total lead count increases by 1. The new lead "Verification Lead" appears in the list (likely on page 1 as most recent).

### TC-LL16: Verify Export button is available for Admin
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Observe the "Export" button next to "Create Lead"
  2. Click "Export"
- **Expected Result:** Export functionality triggers (file download or export action). Data is exported correctly.

### TC-LL17: Verify Action icons — View lead details
- **Preconditions:** User is logged in as Admin. Leads exist in the list.
- **Steps:**
  1. Click the view icon (eye) on any lead row
- **Expected Result:** Lead detail view/modal opens showing all lead information.

### TC-LL18: Verify Action icons — Edit lead
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Click the edit icon (pencil) on any lead row
- **Expected Result:** Edit form/modal opens with pre-populated lead data.

### TC-LL19: Verify Action icons — Delete lead
- **Preconditions:** User is logged in as Admin.
- **Steps:**
  1. Note the lead name in the first row
  2. Click the delete icon (trash) on that row
  3. Confirm deletion (if confirmation dialog appears)
- **Expected Result:** Lead is removed from the list. Total count decreases by 1.

### TC-LL20: Verify Viewer role cannot see Edit/Delete actions
- **Preconditions:** User is logged in as Viewer (`tester@company.com`).
- **Steps:**
  1. Observe the Actions column in the leads table
- **Expected Result:** Only the view icon (eye) is visible. Edit (pencil) and Delete (trash) icons are not displayed.

### TC-LL21: Verify Manager role cannot see Delete action
- **Preconditions:** User is logged in as Manager (`qa@company.com`).
- **Steps:**
  1. Observe the Actions column in the leads table
- **Expected Result:** View (eye) and Edit (pencil) icons are visible. Delete (trash) icon is not displayed.

---

## End-to-End Flow

### TC-E2E01: Login → Create Lead → Verify in List
- **Preconditions:** None (fresh session)
- **Steps:**
  1. Navigate to `https://v0-lead-manager-app.vercel.app`
  2. Login with `admin@company.com` / `Admin@123`
  3. Verify dashboard loads with leads table
  4. Note the total lead count (e.g., "Showing 1 to 10 of 20 leads")
  5. Click "+ Create Lead"
  6. Enter Name: `E2E Test Lead`
  7. Enter Email: `e2e@test.com`
  8. Select Priority: `High`
  9. Click "Create Lead"
  10. Verify success message appears
  11. Verify lead count increased by 1
  12. Search for `E2E Test Lead` in the search bar
  13. Verify the lead appears with correct Name, Email, and Priority
- **Expected Result:** Complete flow succeeds. New lead is visible in the list with all entered data matching.

### TC-E2E02: Login → Create Lead → Logout → Login → Verify Lead Persists
- **Preconditions:** None (fresh session)
- **Steps:**
  1. Login as Admin
  2. Create a lead with Name: `Persist Test`, Email: `persist@test.com`
  3. Verify lead appears in the list
  4. Click "Logout"
  5. Login again as Admin
  6. Search for `Persist Test`
- **Expected Result:** The previously created lead is still present in the list after re-login. Data persists across sessions.

### TC-E2E03: Login as Manager → Create Lead → Verify in List → Verify No Delete
- **Preconditions:** None (fresh session)
- **Steps:**
  1. Login with `qa@company.com` / `password123`
  2. Click "+ Create Lead"
  3. Enter Name: `Manager Lead`, Email: `manager@test.com`
  4. Click "Create Lead"
  5. Verify lead appears in the list
  6. Verify delete icon is not available on the new lead row
- **Expected Result:** Manager can create and view leads but cannot delete them.
