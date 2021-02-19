package com.mark.acl.helper;

import com.alibaba.fastjson.JSONObject;
import com.mark.acl.entity.Permission;
import com.mark.acl.entity.User;
import com.mark.acl.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 木可
 * @version 1.0
 * @date 2021/2/9 13:03
 */
@Component
public class PermissionServiceHelper {

    @Resource
    private UserService userService;

    /**
     * 判断用户是否系统管理员
     * @param userId 用户id
     * @return boolean
     */
    public boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);
        return null != user && "admin".equals(user.getUsername());
    }

    /**
     * 查询级联菜单
     * @param permissions 菜单
     * @return List<Permission>
     */
    public List<Permission> packagePermission(List<Permission> permissions) {
        // 构建最终返回菜单集合
        List<Permission> permissionsResult = new ArrayList<>();
        // 遍历
        for (Permission permission : permissions) {
            // 判断父级id
            if ("0".equals(permission.getPid())) {
                // 设置菜单层级
                permission.setLevel(1);
                // 将该层级菜单添加到集合中
                permissionsResult.add(packageChildrenPermission(permissions, permission));
            }
        }
        return permissionsResult;
    }

    /**
     * 递归查询下级菜单
     * @param permissions 所有菜单数据
     * @param parentPermission 上级菜单
     * @return List<Permission>
     */
    private Permission packageChildrenPermission(List<Permission> permissions, Permission parentPermission) {
        // 对下级进行初始化
        parentPermission.setChildren(new ArrayList<>());

        // 遍历所有菜单数据
        for (Permission permission : permissions) {
            // 判断上级菜单的id与下级菜单的父级id
            if (parentPermission.getId().equals(permission.getPid())) {
                // 说明是该上级菜单的下级菜单
                // 设置下级菜单层级 +1
                permission.setLevel(parentPermission.getLevel() + 1);
                //
                parentPermission.getChildren().add(packageChildrenPermission(permissions, permission));
            }
        }
        return parentPermission;
    }

    /**
     * 封装需要删除的菜单id
     * @param permissions 菜单集合
     * @param pid 菜单id
     * @param idList id集合
     * @return List<String>
     */
    public List<String> packagePermissionId(List<Permission> permissions, String pid, List<String> idList) {
        // 遍历
        for (Permission permission : permissions) {
            // 判断菜单id与子菜单的pid
            if (permission.getPid().equals(pid)) {
                // 说明是该菜单的子菜单，则将子菜单id添加到集合中
                idList.add(permission.getId());
                // 递归调用，获取菜单id
                packagePermissionId(permissions, permission.getId(), idList);
            }
        }
        // 将父级菜单id添加到集合中
        idList.add(pid);
        return idList;
    }

    /**
     * 构建菜单
     * @param treeNodes 菜单列表数据
     * @return List<JSONObject>
     */
    public List<JSONObject> buildMenus(List<Permission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> oneMeunList = topNode.getChildren();
            for(Permission one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("path", one.getPath());
                oneMeun.put("component", one.getComponent());
                oneMeun.put("redirect", "noredirect");
                oneMeun.put("name", "name_"+one.getId());
                oneMeun.put("hidden", false);

                JSONObject oneMeta = new JSONObject();
                oneMeta.put("title", one.getName());
                oneMeta.put("icon", one.getIcon());
                oneMeun.put("meta", oneMeta);

                List<JSONObject> children = new ArrayList<>();
                List<Permission> twoMeunList = one.getChildren();
                for(Permission two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("path", two.getPath());
                    twoMeun.put("component", two.getComponent());
                    twoMeun.put("name", "name_"+two.getId());
                    twoMeun.put("hidden", false);

                    JSONObject twoMeta = new JSONObject();
                    twoMeta.put("title", two.getName());
                    twoMeun.put("meta", twoMeta);

                    children.add(twoMeun);

                    List<Permission> threeMeunList = two.getChildren();
                    for(Permission three :threeMeunList) {
                        if(StringUtils.isEmpty(three.getPath())) {
                            continue;
                        }
                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("path", three.getPath());
                        threeMeun.put("component", three.getComponent());
                        threeMeun.put("name", "name_"+three.getId());
                        threeMeun.put("hidden", true);

                        JSONObject threeMeta = new JSONObject();
                        threeMeta.put("title", three.getName());
                        threeMeun.put("meta", threeMeta);

                        children.add(threeMeun);
                    }
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }
}
