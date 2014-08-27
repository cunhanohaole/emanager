Please follow these steps sequentially to upgrade from version 2.0.4 to 3.0.0

- Shutdown the application
- Run upgrade.sql script located on email-manager-database/scripts/4/3.0.x in the database
- Update all sender config with its user name
- Update all email with its sender config
- Deploy the new version
- Startup the app

