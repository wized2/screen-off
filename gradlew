#!/bin/sh
#
# Copyright © 2015-2021 the original authors.
# Licensed under the Apache License, Version 2.0.
#
set -e
if [ "$1" = "--help" ] || [ "$1" = "-h" ]; then
    exec java "$@" -jar "$0" "$@"
fi
exec java "$@" -jar "$0" "$@"
