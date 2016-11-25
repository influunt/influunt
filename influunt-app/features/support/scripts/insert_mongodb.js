var mongo = require('mongodb');
var assert = require('assert');
var Long = require('mongodb').Long;
var MongoClient = mongo.MongoClient;

var url = 'mongodb://127.0.0.1:27017/influuntdev';
var ObjectId = mongo.ObjectID;
var NumberLong = Long.fromString("1479756629334");

var clearDb = function(db, callback) {
   db.collection('troca_planos_controladores').deleteMany(
      { "_id": ObjectId("58334b5573835c48f22fb7a0") },
      function(err, results) {
         console.log("ObjectId 58334b5573835c48f22fb7a0 apagado com sucesso.");
         callback();
      }
   );
};

var insertDocuments = (db, callback) => {
    var collection = db.collection('troca_planos_controladores');

    var troca_planos_controladores = [ {"_id" : ObjectId("58334b5573835c48f22fb7a0"),
    									"idControlador" : "3d86335e-05e7-4921-8cdf-42ed03821f62",
    									"idAnel" : "043007e5-ee02-4383-bde1-87346abdc895",
    									"timestamp" : NumberLong,
    									"conteudo" : {"momentoDaTroca" : "21/11/2016 17:30:29",
    												  "momentoOriginal" : "21/11/2016 17:30:29",
    												  "anel" : {"posicao" : "1" },
    												  "imposicaoDePlano" : false,
    												  "impostoPorFalha" : true,
    												  "plano" : { "posicao" : "1",
    												  			  "modoOperacao" : "MANUAL",
    												  			  "descricao" : "PLANO 1" }}} ];

    collection.insertMany(troca_planos_controladores, (err, result) => {

        assert.equal(err, null);
        assert.equal(1, result.result.n);
        assert.equal(1, result.ops.length);
        console.log("Troca de planos controladores inseridos");
        callback(result);
    });
};

MongoClient.connect(url, (err, db) => {
  assert.equal(null, err);


  clearDb(db, function() {
  	db.close();
  });

  insertDocuments(db, () => {
		db.close();
  });
});
