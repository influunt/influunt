require_relative 'no_git_strategy'

# config valid only for current version of Capistrano
lock '3.5.0'

set :application, 'influunt-api'
# set :repo_url, 'git@github.com:influunt/influunt.git'

# Default branch is :master
# ask :branch, `git rev-parse --abbrev-ref HEAD`.chomp

# Default deploy_to directory is /var/www/my_app_name
set :deploy_to, '/app/influunt-api'

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

# the same path is used local and remote... just to make things simple for who wrote this.
set :project_tarball_path, Proc.new { Dir.glob('target/universal/influunt-api-*.zip').first }

set :git_strategy, NoGitStrategy

# Finally we need a task to create the tarball and upload it,
namespace :deploy do
  desc 'Create and upload project tarball'
  task :upload_tarball do
    tarball_path = fetch(:project_tarball_path)

    on roles(:all) do
      upload! tarball_path, '/tmp/'
    end
  end

  desc 'Restart the application'
  task :restart do
    on roles(:all) do
      execute "if [[ -f #{shared_path}/influunt.pid ]]; then kill $(cat #{shared_path}/influunt.pid); else echo 'app not running!'; fi"
      execute "#{release_path}/bin/influunt-api -Dconfig.file=#{shared_path}/conf/application.conf > #{shared_path}/logs/startup.log 2>&1 &"
    end
  end
end

namespace :app do
  desc 'Makes the production build'
  task :build do
    run_locally do
      # execute "command -v activator >/dev/null 2>&1 && activator dist || command -v ../activator-1.3.10-minimal/bin/activator >/dev/null 2>&1 && ../activator-1.3.10-minimal/bin/activator dist"
      execute "activator dist"
    end
  end

  desc 'Removes the tarball file'
  task :cleanup do
    run_locally do
      execute "rm #{fetch(:project_tarball_path)}"
    end
  end
end

before 'deploy:updating', 'deploy:upload_tarball'
before 'deploy:upload_tarball', 'app:build'
after 'deploy:published', 'deploy:restart'
after 'deploy:cleanup', 'app:cleanup'

