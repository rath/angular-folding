package com.xrath.plugin.fold;

import com.intellij.icons.AllIcons;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ComponentFileGroup extends ProjectViewNode<PsiFile> {
    private final String name;
    private final String iconType;
    private List<AbstractTreeNode> children;

    protected ComponentFileGroup(Project project, ViewSettings viewSettings,
                                 PsiFile directory, String name, String iconType) {
        super(project, directory, viewSettings);
        this.name = name;
        this.iconType = iconType;
        children = new ArrayList<>();
    }

    @Override
    public boolean contains(@NotNull VirtualFile file) {
        for (AbstractTreeNode childNode : children) {
            ProjectViewNode treeNode = (ProjectViewNode) childNode;
            if (treeNode.contains(file)) {
                return true;
            }
        }
        return false;
    }

    public void addChild(AbstractTreeNode node, String extension) {
        if (node instanceof PsiFileNode) {
            PsiFileNode n  = (PsiFileNode) node;
            children.add(new NamedFileNode(n.getProject(), n.getValue(), n.getSettings(), "." + extension));
        }
    }

    @NotNull
    @Override
    public List<AbstractTreeNode> getChildren() {
        return children;
    }

    @Override
    protected void update(PresentationData presentation) {
        presentation.setPresentableText(name);
        if (iconType.equals("service"))
            presentation.setIcon(AllIcons.Nodes.Static);
        else
            presentation.setIcon(AllIcons.Nodes.Class);
    }

    static class NamedFileNode extends PsiFileNode {
        private final String name;

        public NamedFileNode(Project project, PsiFile psiFile, ViewSettings viewSettings, String name) {
            super(project, psiFile, viewSettings);
            this.name = name;
        }
        @Override
        public void update(PresentationData presentationData) {
            super.update(presentationData);
            presentationData.setPresentableText(this.name);
        }
    }
}
