USER="raro"
HOST="107.170.63.202"
INFLUUNT_ROOT=$(pwd)
INFLUUNT_API="$(pwd)/influunt-api"
ACTIVATOR="$(pwd)/activator-1.3.10-minimal/bin/activator"

# Gerar o executavel da apalicacao e fazer o deploy
cd $INFLUUNT_API && $ACTIVATOR dist
scp -o StrictHostKeyChecking=no -i $INFLUUNT_ROOT/.staging_ssh_private_key $INFLUUNT_API/target/universal/influunt-api-*.zip $USER@$HOST:/tmp/.

# Acessar o servidor e configurar a aplicacao
ssh -o StrictHostKeyChecking=no -i $INFLUUNT_ROOT/.staging_ssh_private_key $USER@$HOST << EOF
  rm -f /app/influunt-api/releases/influunt-api-*.zip
  mv /tmp/influunt-api-*.zip /app/influunt-api/releases/.
  rm -f /tmp/influunt-api-*.zip

  cd /app/influunt-api/releases/.
  unzip influunt-api-*.zip
  chmod +x influunt-api-*/bin/influunt-api

  unlink /app/influunt-api/current/
  ln -s /app/influunt-api/releases/influunt-api-*/ /app/influunt-api/current

  chmod +x /app/influunt-api/current/bin/influunt-api
  rm $INFLUUNT_API/releases/influunt-api-*/RUNNING_PID
  /app/influunt-api/current/bin/influunt-api -Dconfig.file=/app/influunt-api/application.conf
EOF
