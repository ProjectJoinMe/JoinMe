# JoinMe

## How to build

### Build (initial/complete/dependency-change)

1. Open JoinMe project in IntelliJ
2. Open Maven view
3. Execute clean+install on module
4. Refresh dependencies on Maven view (top-left button)

### Build (frontend-application/UI only)
1. Configure "build webpack" run configuration by specifying node interpreter
    - use project provided one under: <project dir>/node/node_bin/node
2. Execute "build webpack"
    - no restart of application necessary, just reload of page

### Build (Java only)
1. Just restart the JoinMe Application with the configuration of your preference, IntelliJ will compile the Java files automatically.

### Run (HSQLDB)
Executing JoinMe with in-memory DB (for development only).
Current known limitations: Search not working as using MySQL-specific function.

1. Execute JoinMeApplication run config

### Run (MySQL)
Executing JoinMe with MySQL DB.
Current known limitations: Search not working as using MySQL-specific function.

1. Prepare MySQL DB
    1. Install MySQL
    2. Execute MySQL Workbench
    3. Login as root
    4. Create "joinme" schema
    5. Create admin user as DBA with all privileges on "joinme" schema
2. Execute "JoinMeApplication (MySQL)" run config

