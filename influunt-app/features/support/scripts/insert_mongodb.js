'use strict';
var mongo = require('mongodb');
var assert = require('assert');
var Long = require('mongodb').Long;
var MongoClient = mongo.MongoClient;

var url = 'mongodb://127.0.0.1:27017/influuntdev';
var ObjectId = mongo.ObjectID;
var NumberLong = Long.fromString('1479756629334');

var trocaPlanosControladores = [
  {'_id' : ObjectId('58334b5573835c48f22fb7a0'),
   'idControlador' : '3d86335e-05e7-4921-8cdf-42ed03821f62',
   'idAnel' : '043007e5-ee02-4383-bde1-87346abdc895',
   'timestamp' : NumberLong,
   'conteudo' : {'momentoDaTroca' : '21/11/2016 17:30:29',
                 'momentoOriginal' : '21/11/2016 17:30:29',
                 'anel' : {'posicao' : '1' },
                 'imposicaoDePlano' : false,
                 'impostoPorFalha' : true,
                 'plano' : { 'posicao' : '1',
                             'modoOperacao' : 'MANUAL',
                             'descricao' : 'PLANO 1' }}}
];

var alarmesFalhasControladores = [
  {
    "_id" : ObjectId("5833538c2d4e4bfb76af0d84"),
    "idControlador" : "3d86335e-05e7-4921-8cdf-42ed03821f62",
    "timestamp" : NumberLong,
    "conteudo" : { "timestamp" : "18/11/2016 20:59:23",
                   "tipoEvento" : {
                   "tipo" : "FALHA_WATCH_DOG",
                   "tipoEventoControlador" : "FALHA",
                   "codigo" : 9,
                   "descricao" : "Falha CPU" },
    "descricaoEvento" : "Falha CPU",
     "params" : []
    }
  }
];

var clearDb = function(db, object, tableName, callback) {
  var id = object[0]._id;

  db.collection(tableName).deleteMany(
    { '_id': ObjectId(id) },
    function() {
      console.log('ObjectId '+id+' apagado com sucesso.');
      callback();
    }
  );
};

var insertDocuments = (db, data, tableName, callback) => {
  var collection = db.collection(tableName);

  collection.insertMany(data, (err, result) => {
    assert.equal(err, null);
    assert.equal(1, result.result.n);
    assert.equal(1, result.ops.length);
    console.log(''+tableName+' inserido com sucesso.');
    callback(result);
  });
};

MongoClient.connect(url, (err, db) => {
  assert.equal(null, err);
  var tableTrocaPlanosControldaores = 'troca_planos_controladores';
  var tableAlarmesFalhasControladores = 'alarmes_falhas_controladores';

  clearDb(db, trocaPlanosControladores, tableTrocaPlanosControldaores, function() {
    db.close();
  });

  clearDb(db, alarmesFalhasControladores, tableAlarmesFalhasControladores, function() {
    db.close();
  });

  insertDocuments(db, trocaPlanosControladores, tableTrocaPlanosControldaores, () => {
    db.close();
  });

  insertDocuments(db, alarmesFalhasControladores, tableAlarmesFalhasControladores, () => {
    db.close();
  });
});
