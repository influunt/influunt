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
    deploy_script = fetch(:deploy_script_name)
    context.execute "chmod +x /tmp/#{deploy_script}"
    context.execute "/tmp/#{deploy_script}"
    context.execute :rm, "/tmp/influunt-*"
  end

  def fetch_revision
    # Return the tarball release id, we are using the git hash of HEAD.
    fetch(:project_release_id)
  end
end
