'use strict';
var mongo = require('mongodb');
var assert = require('assert');
var Long = require('mongodb').Long;
var MongoClient = mongo.MongoClient;

var url = 'mongodb://127.0.0.1:27017/influuntdev';
var ObjectId = mongo.ObjectID;
var NumberLong = Long.fromString('1479756629334');

var MongoInsert = function () {
  var trocaPlanosControladores = [
    {'_id' : ObjectId('58334b5573835c48f22fb7a0'),
     'idControlador' : '279d3e6e-b3ab-4e9f-8358-67e393e5ed0f',
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

  var alarmesFalhasControladores1 = [
    {
      '_id' : ObjectId('5833538c2d4e4bfb76af0d84'),
      'idControlador' : '279d3e6e-b3ab-4e9f-8358-67e393e5ed0f',
      'idAnel' : '043007e5-ee02-4383-bde1-87346abdc895',
      'recuperado' : false,
      'timestamp' : NumberLong,
      'conteudo' : { 'timestamp' : '18/11/2016 20:59:23',
                     'tipoEvento' : {
                     'tipo' : 'FALHA_DETECTOR_VEICULAR_FALTA_ACIONAMENTO',
                     'tipoEventoControlador' : 'FALHA',
                     'descricao' : 'Detector veicular - Falta de acionamento',
                     'descricaoParam' : 'Detector Veicular',
                     'codigo' : 3,
                     'tipoParam' : 'DETECTOR_VEICULAR',
                     'descricao' : 'Detector veicular - Falta de acionamento' },
      'descricaoEvento' : 'Falha CPU',
      'params' : []
      }
    }
  ];

  var alarmesFalhasControladores2 = [
    {
      '_id' : ObjectId('5833538c2d4e4bfb76af0d83'),
      'idControlador' : 'd7c27564-e0b1-4eda-8a6c-2c265ed24c2f',
      'timestamp' : NumberLong,
      'conteudo' : { 'timestamp' : '18/11/2016 20:59:23',
                     'tipoEvento' : {
                     'tipo' : 'FALHA_WATCH_DOG',
                     'tipoEventoControlador' : 'FALHA',
                     'codigo' : 9,
                     'descricao' : 'Falha CPU' },
      'descricaoEvento' : 'Falha CPU',
       'params' : []
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
    var tableTrocaPlanosControldaores = 'trocaPlanosControladores';
    var tableAlarmesFalhasControladores = 'alarmes_falhas_controladores';

    clearDb(db, trocaPlanosControladores, tableTrocaPlanosControldaores, function() {
      db.close();
    });

    clearDb(db, alarmesFalhasControladores1, tableAlarmesFalhasControladores, function() {
      db.close();
    });

    clearDb(db, alarmesFalhasControladores2, tableAlarmesFalhasControladores, function() {
      db.close();
    });

    insertDocuments(db, trocaPlanosControladores, tableTrocaPlanosControldaores, () => {
      db.close();
    });

    insertDocuments(db, alarmesFalhasControladores1, tableAlarmesFalhasControladores, () => {
      db.close();
    });

    insertDocuments(db, alarmesFalhasControladores2, tableAlarmesFalhasControladores, () => {
      db.close();
    });
  });
};
module.exports = MongoInsert;
