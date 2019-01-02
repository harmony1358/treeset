FROM anapsix/alpine-java:8_server-jre
ADD build/distributions/treeset.tar /
ENTRYPOINT ["/treeset/bin/treeset"]
