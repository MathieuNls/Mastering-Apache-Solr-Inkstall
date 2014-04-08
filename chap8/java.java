// Connection 

HttpSolrServer server = new HttpSolrServer(url);
server.setMaxRetries(1);//[1]
server.setConnectionTimeout(5000);//[2]
server.setSoTimeout(1000); //[3]
server.setDefaultMaxConnectionsPerHost(100);//[4]
server.setMaxTotalConnections(100);//[5]
server.setFollowRedirects(false);//[6]
server.setAllowCompression(true);//[7]

// add document

SolrInputDocumentmyDocument = new SolrInputDocument();
myDocument.addField("id", "id2", 1.0f);
myDocument.addField("name", "doc2", 1.0f);
myDocument.addField("price", 20);

server.add(myDocument);
server.commit();

// collection

List<SolrInputDocument>myDocuments = new ArrayList<SolrInputDocument>();
SolrInputDocumentmyDoc = new SolrInputDocument();
for (inti = 0; i< 10; i++) {
	myDoc.addField("id" + i, "thing" + i, 18.0f);
	myDoc.addField("name_" + i, "doc" + i, 1.0f);
	myDoc.addField("price", 20 * i);
	myDocuments.add(myDoc);
}
server.add(myDocuments);
server.commit();

// update

UpdateRequest updateRequest = new UpdateRequest();
updateRequest.setAction(UpdateRequest.ACTION.COMMIT, false, false);
SolrInputDocument myDocumentInstantlycommited = new SolrInputDocument();
myDocumentInstantlycommited.addField("foo", "bar");
updateRequest.add(myDocumentInstantlycommited);
UpdateResponse rsp = updateRequest.process(server);

// Delete

server.deleteById("1"); //[1]
server.deleteById(idsToDelete); //[2] idsToDelete being a List<String>
server.deleteByQuery(":*"); //[3]
server.deleteById("1", 1000); //[4, 5, 6]
server.deleteById(idsToDelete, 1000); // Commit within 1000 ms
server.deleteByQuery("*:*", 1000); 

// Query

SolrQuery query = new SolrQuery();
query.setQuery("*:*");
query.addField("price");

QueryResponse queryResponse = server.query(query);

SolrDocumentList docs = queryResponse.getResults();
Iterator<SolrDocument> iterator = docs.iterator();
while(iterator.hasNext()){
	System.out.println(iterator.next().getFieldValue("price"));
}

// Query Instruction

SolrQuery solrQuery = new  SolrQuery().
	setQuery("Lou Reed").
	addFacetField("Title").
	setFacet(true).
	setFacetLimit(8).
	setFacetMinCount(1).
	addFacetField("Year").
	setHighlight(true);

