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
# set :linked_files, fetch(:linked_files, []).push('config/database.yml', 'config/secrets.yml')

# Default value for linked_dirs is []
# set :linked_dirs, fetch(:linked_dirs, []).push('log', 'tmp/pids', 'tmp/cache', 'tmp/sockets', 'public/system')

# Default value for default_env is {}
# set :default_env, { path: "/opt/ruby/bin:$PATH" }

# Default value for keep_releases is 5
# set :keep_releases, 5

# release id is just the commit hash used to create the tarball.
set :project_release_id, `git log --pretty=format:'%h' -n 1 staging`

# the same path is used local and remote... just to make things simple for who wrote this.
set :project_tarball_path, Dir.glob('target/universal/influunt-api-*.zip').first

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
end

# Attach our upload_tarball task to Capistrano deploy task chain.
before 'deploy:updating', 'deploy:upload_tarball'
