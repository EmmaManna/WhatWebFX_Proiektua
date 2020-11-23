INSERT IGNORE INTO request_configs (value) VALUES ('{"headers":{"User-Agent":"WhatWeb/0.5.0"}}');
INSERT IGNORE INTO targets (status,target) VALUES ('200','https://twitter.com/');
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Cookies'), '','','guest_id,personalization_id','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Country'), '','','RESERVED','','','','ZZ','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'HTML5'), '','','','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'HTTPServer'), '','','tsa_f','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'IP'), '','','104.244.42.129','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Open-Graph-Protocol'), '','','','','','','2231777543','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'OpenSearch'), '','','/opensearch.xml','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Script'), '','','text/javascript','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Strict-Transport-Security'), '','','max-age=631138519','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'UncommonHeaders'), '','','content-security-policy,cross-origin-opener-policy,expiry,x-connection-hash,x-content-type-options,x-response-time','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-Frame-Options'), '','','DENY','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-Powered-By'), '','','Express','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-XSS-Protection'), '','','0','','','','','','' );
INSERT IGNORE INTO request_configs (value) VALUES ('{"headers":{"User-Agent":"WhatWeb/0.5.0"}}');
INSERT IGNORE INTO targets (status,target) VALUES ('200','https://twitter.com/');
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Cookies'), '','','guest_id,personalization_id','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Country'), '','','RESERVED','','','','ZZ','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'HTML5'), '','','','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'HTTPServer'), '','','tsa_f','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'IP'), '','','104.244.42.129','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Open-Graph-Protocol'), '','','','','','','2231777543','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'OpenSearch'), '','','/opensearch.xml','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Script'), '','','text/javascript','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'Strict-Transport-Security'), '','','max-age=631138519','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'UncommonHeaders'), '','','content-security-policy,cross-origin-opener-policy,expiry,x-connection-hash,x-content-type-options,x-response-time','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-Frame-Options'), '','','DENY','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-Powered-By'), '','','Express','','','','','','' );
INSERT INTO scans (target_id, config_id, plugin_id, version, os, string, account, model, firmware, module, filepath, certainty) VALUES ( (SELECT target_id from targets WHERE target = 'https://twitter.com/'),(SELECT MAX(config_id) from request_configs),(SELECT plugin_id from plugins WHERE name = 'X-XSS-Protection'), '','','0','','','','','','' );