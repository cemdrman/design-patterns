"""MkDocs hook: links from the docs to source code (outside docs/) become GitHub links."""
import os
import re

REPO_URL = "https://github.com/cemdrman/design-patterns"
BRANCH = "master"
LINK = re.compile(r"\]\((?!https?:|mailto:|#)([^)\s]+)\)")


def on_page_markdown(markdown, page, config, files):
    project_root = os.path.dirname(config.config_file_path)
    docs_dir = os.path.relpath(config.docs_dir, project_root)
    page_dir = os.path.join(docs_dir, os.path.dirname(page.file.src_path))

    def rewrite(match):
        target, _, anchor = match.group(1).partition("#")
        path = os.path.normpath(os.path.join(page_dir, target))
        if not path.startswith(".." + os.sep) and not path.startswith(docs_dir + os.sep) and path != docs_dir:
            kind = "tree" if os.path.isdir(os.path.join(project_root, path)) else "blob"
            url = f"{REPO_URL}/{kind}/{BRANCH}/{path.replace(os.sep, '/')}"
            return f"]({url}{'#' + anchor if anchor else ''})"
        return match.group(0)

    return LINK.sub(rewrite, markdown)
