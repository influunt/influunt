#!/bin/bash

unzip -q -o /tmp/influunt-central-0.1.0.zip -d /app/influunt/releases/20170131141907
mv /app/influunt/releases/20170131141907/influunt-central-0.1.0/* /app/influunt/releases/20170131141907
rm -r /app/influunt/releases/20170131141907/influunt-central-0.1.0
chmod +x /app/influunt/releases/20170131141907/bin/influunt-central
rm /tmp/influunt-central-0.1.0.zip