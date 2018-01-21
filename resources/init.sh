#!/usr/bin/env bash
echo "CREATE DATABASE MRA" | mysql -u root -p
mysql mra -u root -p < ./groupdb.sql
mysql mra -u root -p < ./inputs.sql