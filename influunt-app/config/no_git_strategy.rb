module NoGitStrategy
  def check
    true
  end

  def test
    # Check if the tarball was uploaded.
    test! " [ -f #{fetch(:project_tarball_path)} ] "
  end

  def clone
    false
  end

  def update
    true
  end

  def release
    filename = fetch(:project_tarball_path)
    # dist_name = filename.split('.')[0..-2].join('.')
    # Unpack the tarball uploaded by deploy:upload_tarball task.
    # context.execute "tar -xf /tmp/#{filename} -C #{release_path} && mv #{release_path}/#{dist_name}/* #{release_path} && rm -r #{release_path}/#{dist_name}"
    context.execute "tar xzf /tmp/#{filename} -C /tmp"
    context.execute "mv /tmp/dist/* #{release_path}"
    context.execute "rm -r /tmp/dist"
  end

  def fetch_revision
    # Return the tarball release id, we are using the git hash of HEAD.
    fetch(:project_release_id)
  end
end
