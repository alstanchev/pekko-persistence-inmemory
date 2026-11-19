#!/bin/bash
# compile and test against all crossScalaVersions
sbt -batch clean +test
