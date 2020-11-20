BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "plugins" (
                                         "plugin_id"	INTEGER NOT NULL,
                                         "name"	TEXT NOT NULL UNIQUE,
                                         PRIMARY KEY("plugin_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "scans" (
                                       "scan_id"	INTEGER NOT NULL,
                                       "config_id"	INTEGER NOT NULL,
                                       "plugin_id"	INTEGER NOT NULL,
                                       "target_id"	INTEGER NOT NULL,
                                       "version"	TEXT,
                                       "os"	TEXT,
                                       "string"	TEXT,
                                       "account"	TEXT,
                                       "model"	TEXT,
                                       "firmware"	TEXT,
                                       "module"	TEXT,
                                       "filepath"	TEXT,
                                       "certainty"	TEXT,
                                       PRIMARY KEY("scan_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "request_configs" (
                                                 "config_id"	INTEGER NOT NULL,
                                                 "value"	TEXT NOT NULL,
                                                 PRIMARY KEY("config_id" AUTOINCREMENT)
);
CREATE TABLE IF NOT EXISTS "targets" (
                                         "target_id"	INTEGER NOT NULL,
                                         "target"	TEXT NOT NULL,
                                         "status"	TEXT,
                                         "lastUpdated"	TEXT,
                                         PRIMARY KEY("target_id" AUTOINCREMENT),
                                         UNIQUE("target","status")
);
COMMIT;
