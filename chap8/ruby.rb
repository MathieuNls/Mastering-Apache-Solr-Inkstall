require 'rubygems'
require 'rsolr'
solr = RSolr.connect :url => 'http://localhost:8983/solr/'

solr.add :id=>2, :name=>'Lou Reed'

# search 

solr = RSolr.connect :url => 'http://localhost:8983/solr/'
response = solr.get 'select', :params => {
	:q=>'*:*',
	:start=>0,
	:rows=>10
	}
response["response"]["docs"].each{|doc| puts doc["id"] }

