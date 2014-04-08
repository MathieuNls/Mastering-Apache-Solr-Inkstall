<?php
define('SOLR_SERVER_HOSTNAME', 'solr.example.com'); //[1]
define('SOLR_SECURE', true); //[2]
define('SOLR_SERVER_PORT', ((SOLR_SECURE) ? 8443 : 8983)); //[3]
define('SOLR_SERVER_USERNAME', 'admin'); //[4]
define('SOLR_SERVER_PASSWORD', 'changeit'); //[5]
define('SOLR_SERVER_TIMEOUT', 10); //[6]

include "bootstrap.php";

// Login
$options = array
(
    'hostname' => SOLR_SERVER_HOSTNAME,
    'login'    => SOLR_SERVER_USERNAME,
    'password' => SOLR_SERVER_PASSWORD,
    'port'     => SOLR_SERVER_PORT,
);

$client = new SolrClient($options);
$doc = new SolrInputDocument();


// new document
$doc->addField('id',2);
$doc->addField('Name', 'Lou Reed');
$doc->addField('Song', 'Walk on the wilde side');

$client->addDocument($doc);

//Search

$query = new SolrQuery();
$query->setQuery('*:*');
$query->setStart(0);
$query->setRows(10);
$query_response = $client->query($query);
$response = $query_response->getResponse();
print_r($response);

// Advanced search

$query = new SolrQuery();
$query->setQuery('Lou Reed');
$query->setStart(0);
$query->setRows(10);
$query->addField('SongTitle')->addField('Date');
$query_response = $client->query($query);
$response = $query_response->getResponse();
print_r($response);

$client = new SolrClient($options);
$query = new SolrQuery('*:*');
$query->setFacet(true);
$query->addFacetField('Song')->addFacetField('Date')->setFacetMinCount(2);
$updateResponse = $client->query($query);
$response_array = $updateResponse->getResponse();
$facet_data = $response_array->facet_counts->facet_fields;
print_r($facet_data);



?>