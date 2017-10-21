package com.xrath.plugin.fold;

import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeStructureProvider implements com.intellij.ide.projectView.TreeStructureProvider {
    private final Pattern namePattern =
            Pattern.compile("(.*)\\.component\\.(css|sass|scss|stylus|styl|less|html|pug|ts)", Pattern.CASE_INSENSITIVE);

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent, @NotNull Collection<AbstractTreeNode> children, ViewSettings viewSettings) {
        if (!(parent.getValue() instanceof PsiDirectory))
            return children;

        List<AbstractTreeNode> ret = new ArrayList<>();
        Map<String, ComponentFileGroup> map = new HashMap<>();
        for (AbstractTreeNode child : children) {
            if (!(child.getValue() instanceof PsiFile)) {
                ret.add(child);
                continue;
            }

            PsiFile psiFile = (PsiFile) child.getValue();
            String filename = psiFile.getName();
            Matcher m = namePattern.matcher(filename);
            if (!m.find()) {
                ret.add(child);
                continue;
            }

            String prefix = m.group(1);
            String extension = m.group(2);
            ComponentFileGroup group = map.get(prefix);
            if (group == null) {
                group = new ComponentFileGroup(child.getProject(), viewSettings, psiFile, prefix + ".component");
                map.put(prefix, group);
                ret.add(group);
            }
            group.addChild(child, extension);
        }
        return ret;
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> selected, String dataName) {
        return null;
    }
}
