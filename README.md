# EAN lookup client

A program that for a given file (a text file with one product name/description per line) will look up the EAN code(13 digit code) in a given database. The heuristics should be that if the product is not found in one DB it will, as a fall back try in next DB (which is beyond the scope of this test though). The look up should not be hardcoded but rather be wrapped with internal API thus transparent to the program. The lookup is an HTTP communication with latency, so in order to go through the list as fast as possible, multithreaded lookups should be used (using java's Future and thread pool).

As the DB :

http://world.openfoodfacts.org/cgi/search.pl?search_terms=boursin&search_simple=1&action=process

The output of this test should be a JSON containing key:value pairs with the original product name, the EAN code and the description of the associated product (found in the DB). In case the EAN is not found, add only the original product description in the output file.
