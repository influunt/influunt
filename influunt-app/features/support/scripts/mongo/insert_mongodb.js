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
     'idControlador' : '66f6865f-6963-4ff5-b160-7c6febb68c03',
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
      'idControlador' : '66f6865f-6963-4ff5-b160-7c6febb68c03',
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
      'params' : [1, false]
      }
    }
  ];

  var alarmesFalhasControladores2 = [
    {
      '_id' : ObjectId('5833538c2d4e4bfb76af0d83'),
      'idControlador' : 'd7c27564-e0b1-4eda-8a6c-2c265ed24c2f',
      'idAnel' : '33e2bbf0-72ad-4d11-98d8-1bb440c370b0',
      'recuperado' : false,
      'timestamp' : NumberLong,
      'conteudo' : { 'timestamp' : '18/11/2016 20:59:23',
                     'tipoEvento' : {
                     'tipo' : 'FALHA_WATCH_DOG',
                     'tipoEventoControlador' : 'FALHA',
                     'descricaoParam' : 'Detector Veicular',
                     'tipoParam' : 'DETECTOR_VEICULAR',
                     'codigo' : 9,
                     'descricao' : 'Falha CPU' },
      'descricaoEvento' : 'Falha CPU',
       'params' : [1, false]
      }
    }
  ];

  var statusControlador = [
    {
      '_id' : ObjectId('57dafa79dd605a97ce6003e4'),
      'idControlador' : '3c802c00-6d7f-423d-b6e7-cfc8e744c8ae',
      'timestamp' : NumberLong,
      'conectado' : true
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
    var tableStatusControladores = 'status_conexao_controlador';

    clearDb(db, trocaPlanosControladores, tableTrocaPlanosControldaores, function() {
      db.close();
    });

    clearDb(db, statusControlador, tableStatusControladores, function() {
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

    insertDocuments(db, statusControlador, tableStatusControladores, () => {
      db.close();
    });
  });
};
module.exports = MongoInsert;
