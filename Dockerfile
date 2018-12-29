FROM anapsix/alpine-java
MAINTAINER Bartosz Wojcik <b.wojcik@ximago.pl>
ADD build/distributions/treeset.tar /
ENTRYPOINT ["/treeset/bin/treeset"]
