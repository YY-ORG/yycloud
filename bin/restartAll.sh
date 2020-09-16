#!/bin/sh

/root/yycloud/bin/base/yycloud-discovery.sh restart
sleep 10s
/root/yycloud/bin/base/yycloud-edgeserver.sh restart
/root/yycloud/bin/base/yycloud-auth.sh restart
sleep 5s
/root/yycloud/bin/api/yycloud-adminui.sh restart
/root/yycloud/bin/core/yycloud-assess.sh restart
/root/yycloud/bin/core/yycloud-filesys.sh restart
/root/yycloud/bin/core/yycloud-finance.sh restart
/root/yycloud/bin/core/yycloud-sysbase.sh restart
/root/yycloud/bin/core/yycloud-usermgmt.sh restart
