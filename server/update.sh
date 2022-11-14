#!/bin/bash
# Exit if command fails
set -e
# keep track of the last executed command
trap 'last_command=$current_command; current_command=$BASH_COMMAND' DEBUG
# echo an error message before exiting
trap 'echo "\"${last_command}\" command filed with exit code $?."' EXIT

# OMIM 2 GENE
wget -P server/src/main/resources/ -N ftp://ftp.ncbi.nlm.nih.gov/gene/DATA/mim2gene_medgen

# HPO OBO
wget -P server/src/main/resources/ -N http://purl.obolibrary.org/obo/hp.json

# MAXO OBO
wget -P server/src/main/resources/ -N https://raw.githubusercontent.com/monarch-initiative/MAxO/master/maxo.json


# ORPHANET 2 GENE
wget -P server/src/main/resources/ -N http://www.orphadata.org/data/xml/en_product6.xml

# GENE INFO
wget -P server/src/main/resources/ -N http://ftp.ebi.ac.uk/pub/databases/genenames/hgnc/tsv/hgnc_complete_set.txt

# PHENOTYPE HPOA
wget -P server/src/main/resources/ -N https://github.com/obophenotype/human-phenotype-ontology/releases/latest/download/phenotype.hpoa

# EXIT
exit 0;