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
            Pattern.compile("(.*)\\.(component|service|pipe|guard|directive|actions|effects|reducer|selectors|state|resolver|page)\\.(css|sass|scss|stylus|styl|less|html|svg|haml|pug|spec|stories\\.ts|ts)", Pattern.CASE_INSENSITIVE);

    @Override
    public @NotNull Collection<AbstractTreeNode<?>> modify(
            @NotNull AbstractTreeNode<?> parent,
            @NotNull Collection<AbstractTreeNode<?>> children,
            ViewSettings viewSettings) {

        if (!(parent.getValue() instanceof PsiDirectory))
            return children;

        List<AbstractTreeNode<?>> ret = new ArrayList<>();
        Map<String, ComponentFileGroup> map = new HashMap<>();

        for (AbstractTreeNode<?> child : children) {
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
            String classType = m.group(2);
            String extension = m.group(3);
            final String groupKey = String.format("%s.%s", prefix, classType);

            ComponentFileGroup group = map.get(groupKey);
            if (group == null) {
                group = new ComponentFileGroup(child.getProject(), viewSettings,
                        psiFile, groupKey, classType);
                map.put(groupKey, group);
                ret.add(group);
            }
            group.addChild(child, extension);
        }

        // Undo grouping that has only child
        for (int i = 0, len = ret.size(); i < len; i++) {
            AbstractTreeNode<?> added = ret.get(i);
            if (added instanceof ComponentFileGroup) {
                ComponentFileGroup g = (ComponentFileGroup) added;
                if (g.getChildrenCount() <= 1) {
                    ret.set(i, g.getOriginalFirstChild());
                }
            }
        }

        return ret;
    }

    @Override
    public @Nullable Object getData(@NotNull Collection<AbstractTreeNode<?>> selected, @NotNull String dataId) {
        return com.intellij.ide.projectView.TreeStructureProvider.super.getData(selected, dataId);
    }
}
