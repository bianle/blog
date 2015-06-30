---
layout: post
title: java 收集
category: java
comments: false
---

### maven

* maven 项目转 eclipse Dynamic Web Project
1. mvn 转 java项目
```
mvn eclipse:eclipse
```

2. java添加web特性

`Project properties` => `Project Facets`
点击 `Convert to faceted form`
选中 `Dynamic Web Module`
点击 `Further Configuration available` 修改 `content directory` 为 `src\main\webapp`

​3. mvn 管理的 jar 加到部署集

`Project properties` => `Deployment Assembly` => Add => `Java Build Path Entries` 选中M2_REPO，确定

* unbound classpath variable: 'M2REPO/xxx/xxx.jar'

`Eclipse`->`Windows`->`Preferences`->`java`->`Build Path`->`Classpath Variables`
new 一个，名为`M2_REPO` ，值为 `～/.m2/repository`

### list 封装成 java bean

原理很简单：人群(list)中每个人依次在该人群中找爹(for嵌套)，找到为止（break）。

list 形如 ：

```
|id  |pid  |name  |
|1   |     |1     |
|11  |1    |1-1   |
|111 |11   |1-1-1 |
|2   |     |2     |
```

java bean **tree.TreeNode** 代码如下：

```java
package tree;
import java.util.List;

public class TreeNode {
        private String id;
        private String name;
        private String parentId;
        private boolean leaf = true;
        private List<TreeNode> children;

    /** Getters and Setters  **/
}

```

测试代码 **tree.Test** 如下：

```java

package tree;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Test {

        public static List<TreeNode> list2tree(List<TreeNode> list){
                List<TreeNode> result = new ArrayList<TreeNode>();
                for(TreeNode node1 : list){
                        boolean root = true;
                        for(TreeNode node2 : list){//找爹
                                if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){
                                        root = false;//不是祖先
                                        if(node2.getChildren() == null){
                                                node2.setChildren(new ArrayList<TreeNode>());
                                        }
                                        node2.getChildren().add(node1); //认爹
                                        node2.setLeaf(false);
                                        break;//爹就一个，找到就不找了
                                }
                        }
                        if(root){//祖先
                                result.add(node1);
                        }
                }
                return result;
        }

        public static void main(String[] args) {
                List<TreeNode> menuList = new ArrayList();
                TreeNode n1 = new TreeNode();
                n1.setId("1");
                n1.setName("1");
                n1.setParentId(null);

                TreeNode n2 = new TreeNode();
                n2.setId("11");
                n2.setName("1-1");
                n2.setParentId("1");

                TreeNode n3 = new TreeNode();
                n3.setId("111");
                n3.setName("1-1-1");
                n3.setParentId("11");

                TreeNode n4 = new TreeNode();
                n4.setId("2");
                n4.setName("2");
                n4.setParentId(null);

                menuList.add(n1);
                menuList.add(n3);
                menuList.add(n2);
                menuList.add(n4);

                menuList = list2tree(menuList);

                //转为json格式
                String json = new Gson().toJson(menuList);
                System.out.println("json:"+json);
        }
}


```

测试代码输出**结果**：

```json
json:[{"id":"1",
       "name":"1",
       "leaf":false,
       "children":[{"id":"11",
                    "name":"1-1",
                    "parentId":"1",
                    "leaf":false,
                    "children":[{"id":"111",
                                 "name":"1-1-1",
                                 "parentId":"11",
                                 "leaf":true
                                 }]
                    }]
       },
       {"id":"2",
       "name":"2",
       "leaf":true
       }]

```
