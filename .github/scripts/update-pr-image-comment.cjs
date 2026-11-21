module.exports = async ({ github, context }) => {
  const marker = '<!-- projection-service-preview-image -->';
  const imageName = process.env.IMAGE_NAME;
  const previewTag = process.env.PREVIEW_TAG;
  const imageDigest = process.env.IMAGE_DIGEST;

  const body = `${marker}
## Projection-service preview image

Latest preview for this PR (moves when the PR changes):
\`${imageName}:${previewTag}\`

Exact immutable image:
\`${imageName}@${imageDigest}\`

This package is private, so a Docker client needs GHCR read access to pull it.`;

  const { owner, repo } = context.repo;
  const issue_number = context.issue.number;
  const comments = await github.paginate(github.rest.issues.listComments, {
    owner,
    repo,
    issue_number
  });
  const existing = comments.find(comment => comment.body?.includes(marker));

  if (existing) {
    await github.rest.issues.updateComment({ owner, repo, comment_id: existing.id, body });
  } else {
    await github.rest.issues.createComment({ owner, repo, issue_number, body });
  }
};
