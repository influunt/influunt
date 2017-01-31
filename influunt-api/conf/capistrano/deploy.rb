require_relative 'no_git_strategy'

# config valid only for current version of Capistrano
lock '3.5.0'

set :application, 'influunt'
# set :repo_url, 'git@github.com:influunt/influunt.git'

# Default branch is :master
# ask :branch, `git rev-parse --abbrev-ref HEAD`.chomp

# Default deploy_to directory is /var/www/my_app_name
set :deploy_to, '/app/influunt'

# Default value for :scm is :git
# set :scm, :git

# Default value for :format is :airbrussh.
# set :format, :airbrussh

# You can configure the Airbrussh format using :format_options.
# These are the defaults.
set :format_options, command_output: true, log_file: 'log/capistrano.log', color: :auto, truncate: false

# Default value for :pty is false
# set :pty, true

# Default value for :linked_files is []
# set :linked_files, fetch(:linked_files, []).push('conf/application.conf')

# Default value for linked_dirs is []
set :linked_dirs, fetch(:linked_dirs, []).push('logs', 'imagens')

# Default value for default_env is {}
# set :default_env, { path: "/opt/ruby/bin:$PATH" }

# Default value for keep_releases is 5
# set :keep_releases, 5

# release id is just the commit hash used to create the tarball.
set :project_release_id, `git log --pretty=format:'%h' -n 1 staging`

set :api_tarball_path, Proc.new { Dir.glob('modules/influunt-api/target/universal/influunt-api-*.zip').first }
set :central_tarball_path, Proc.new { Dir.glob('modules/influunt-central/target/universal/influunt-central-*.zip').first }

set :deploy_script_name, 'influunt-deploy.sh'

set :git_strategy, NoGitStrategy

def create_deploy_script(role)
  filename = fetch(:"#{role}_tarball_path").split('/').last
  dist_name = filename.split('.')[0..-2].join('.')
  exec_name = dist_name.split('-')[0..-2].join('-')
  script  = "#!/bin/bash\n\n"
  script += "unzip -q -o /tmp/#{filename} -d #{release_path}\n"
  script += "mv #{release_path}/#{dist_name}/* #{release_path}\n"
  script += "rm -r #{release_path}/#{dist_name}\n"
  script += "chmod +x #{release_path}/bin/#{exec_name}\n"
  script += "rm /tmp/#{filename}"
  File.write(fetch(:deploy_script_name), script)
end

# Finally we need a task to create the tarball and upload it,
namespace :deploy do
  desc 'Upload project tarball'
  task :upload_tarball do
    on roles(:api) do
      create_deploy_script(:api)
      upload! fetch(:deploy_script_name), '/tmp/'
      upload! fetch(:api_tarball_path), '/tmp/'
    end

    on roles(:central) do
      create_deploy_script(:central)
      upload! fetch(:deploy_script_name), '/tmp/'
      upload! fetch(:central_tarball_path), '/tmp/'
    end
  end

  desc 'Restart the application'
  task :restart do
    on roles(:api) do
      execute "if [[ -f #{shared_path}/influunt.pid ]]; then kill $(cat #{shared_path}/influunt.pid); else echo 'app not running!'; fi"
      execute "#{release_path}/bin/influunt-api -Dconfig.file=#{shared_path}/conf/application.conf > #{shared_path}/logs/startup.log 2>&1 &"
    end

    on roles(:central) do
      execute "if [[ -f #{shared_path}/influunt.pid ]]; then kill $(cat #{shared_path}/influunt.pid); else echo 'app not running!'; fi"
      execute "#{release_path}/bin/influunt-central -Dconfig.file=#{shared_path}/conf/application.conf > #{shared_path}/logs/startup.log 2>&1 &"
    end
  end
end

namespace :app do
  desc 'Makes the production build'
  task :build do
    run_locally do
      execute 'command -v activator >/dev/null 2>&1; if [ "$?" -eq 0 ]; then activator dist; else ../activator-1.3.10-minimal/bin/activator dist; fi;'
    end
  end

  desc 'Removes the tarball file'
  task :cleanup do
    run_locally do
      execute "rm #{fetch(:api_tarball_path)}"
      execute "rm #{fetch(:central_tarball_path)}"
      execute "rm #{fetch(:deploy_script_name)}"
    end
  end
end

before 'deploy:updating', 'deploy:upload_tarball'
before 'deploy:upload_tarball', 'app:build'
after 'deploy:published', 'deploy:restart'
after 'deploy:cleanup', 'app:cleanup'
