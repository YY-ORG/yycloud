#!/bin/sh

/root/yycloud/bin/base/yycloud-edgeserver.sh stop
/root/yycloud/bin/base/yycloud-auth.sh stop
sleep 5s
/root/yycloud/bin/api/yycloud-adminui.sh stop
/root/yycloud/bin/core/yycloud-assess.sh stop
/root/yycloud/bin/core/yycloud-filesys.sh stop
/root/yycloud/bin/core/yycloud-finance.sh stop
/root/yycloud/bin/core/yycloud-sysbase.sh stop
/root/yycloud/bin/core/yycloud-usermgmt.sh stop
sleep 10s
/root/yycloud/bin/base/yycloud-discovery.sh stop