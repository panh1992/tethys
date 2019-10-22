/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 110002
 Source Host           : localhost:5432
 Source Catalog        : test
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 110002
 File Encoding         : 65001

 Date: 22/10/2019 16:59:07
*/


-- ----------------------------
-- Table structure for athena_file
-- ----------------------------
DROP TABLE IF EXISTS "public"."athena_file";
CREATE TABLE "public"."athena_file" (
  "id" int8 NOT NULL,
  "store_id" int8,
  "creater_id" int8,
  "store_space" varchar(128) COLLATE "pg_catalog"."default",
  "file_name" varchar(255) COLLATE "pg_catalog"."default",
  "file_size" int8,
  "source_id" int8,
  "source_type" varchar(32) COLLATE "pg_catalog"."default",
  "is_dir" bool,
  "check_sum" varchar(255) COLLATE "pg_catalog"."default",
  "format" varchar(32) COLLATE "pg_catalog"."default",
  "status" varchar(16) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6),
  "modify_time" timestamptz(6),
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."athena_file" OWNER TO "test";
COMMENT ON COLUMN "public"."athena_file"."id" IS '文件主键';
COMMENT ON COLUMN "public"."athena_file"."store_id" IS '存储空间';
COMMENT ON COLUMN "public"."athena_file"."creater_id" IS '创建用户';
COMMENT ON COLUMN "public"."athena_file"."store_space" IS '文件所属存储空间名称';
COMMENT ON COLUMN "public"."athena_file"."file_name" IS '文件名';
COMMENT ON COLUMN "public"."athena_file"."file_size" IS '用户角度文件大小     单位 byte';
COMMENT ON COLUMN "public"."athena_file"."source_id" IS '存储来源';
COMMENT ON COLUMN "public"."athena_file"."source_type" IS '存储来源类型  upload   mount   system    task';
COMMENT ON COLUMN "public"."athena_file"."is_dir" IS '是否为目录';
COMMENT ON COLUMN "public"."athena_file"."check_sum" IS '文件的校验码';
COMMENT ON COLUMN "public"."athena_file"."format" IS '文件格式      以文件扩展名为依据';
COMMENT ON COLUMN "public"."athena_file"."status" IS '文件状态      new   uploading   available   failed   deleted';
COMMENT ON COLUMN "public"."athena_file"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."athena_file"."modify_time" IS '修改时间';
COMMENT ON COLUMN "public"."athena_file"."description" IS '描述信息';
COMMENT ON TABLE "public"."athena_file" IS '文件表';

-- ----------------------------
-- Table structure for multipolar_store
-- ----------------------------
DROP TABLE IF EXISTS "public"."multipolar_store";
CREATE TABLE "public"."multipolar_store" (
  "id" int8 NOT NULL,
  "file_id" int8,
  "status" int4,
  "level" int4,
  "is_active" bool,
  "store_path" text COLLATE "pg_catalog"."default",
  "store_size" int8,
  "create_time" timestamptz(6),
  "modify_time" timestamptz(6)
)
;
ALTER TABLE "public"."multipolar_store" OWNER TO "test";
COMMENT ON COLUMN "public"."multipolar_store"."id" IS '多级存储主键';
COMMENT ON COLUMN "public"."multipolar_store"."file_id" IS '文件';
COMMENT ON COLUMN "public"."multipolar_store"."status" IS '实际存储在所属层级的状态      uploading, available, deleted';
COMMENT ON COLUMN "public"."multipolar_store"."level" IS '存储级别';
COMMENT ON COLUMN "public"."multipolar_store"."is_active" IS '是否是当前激活层级';
COMMENT ON COLUMN "public"."multipolar_store"."store_path" IS '实际存储路径';
COMMENT ON COLUMN "public"."multipolar_store"."store_size" IS '存储文件大小';
COMMENT ON COLUMN "public"."multipolar_store"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."multipolar_store"."modify_time" IS '修改时间';
COMMENT ON TABLE "public"."multipolar_store" IS '文件的多级存储';

-- ----------------------------
-- Table structure for path_tree
-- ----------------------------
DROP TABLE IF EXISTS "public"."path_tree";
CREATE TABLE "public"."path_tree" (
  "id" int8 NOT NULL,
  "ancestor_id" int8,
  "descendant_id" int8,
  "depth" int8
)
;
ALTER TABLE "public"."path_tree" OWNER TO "test";
COMMENT ON COLUMN "public"."path_tree"."ancestor_id" IS '祖先文件';
COMMENT ON COLUMN "public"."path_tree"."descendant_id" IS '子孙文件';
COMMENT ON COLUMN "public"."path_tree"."depth" IS '相对层级';
COMMENT ON TABLE "public"."path_tree" IS '文件树形关系 （采用闭包表）';

-- ----------------------------
-- Table structure for store_backends
-- ----------------------------
DROP TABLE IF EXISTS "public"."store_backends";
CREATE TABLE "public"."store_backends" (
  "id" int8 NOT NULL,
  "name" varchar(128) COLLATE "pg_catalog"."default",
  "protocol" varchar(128) COLLATE "pg_catalog"."default",
  "container" varchar(255) COLLATE "pg_catalog"."default",
  "endpoint" varchar(255) COLLATE "pg_catalog"."default",
  "port" int4,
  "level" int4,
  "is_active" bool,
  "auth_type" int4,
  "auth_params_1" varchar(255) COLLATE "pg_catalog"."default",
  "auth_params_2" varchar(255) COLLATE "pg_catalog"."default",
  "auth_params_3" varchar(255) COLLATE "pg_catalog"."default",
  "auth_params_4" varchar(255) COLLATE "pg_catalog"."default",
  "create_time" timestamptz(6),
  "modify_time" timestamptz(6),
  "description" varchar(255) COLLATE "pg_catalog"."default"
)
;
ALTER TABLE "public"."store_backends" OWNER TO "test";
COMMENT ON COLUMN "public"."store_backends"."id" IS '存储后端主键';
COMMENT ON COLUMN "public"."store_backends"."name" IS '存储后端名称';
COMMENT ON COLUMN "public"."store_backends"."protocol" IS '存储协议';
COMMENT ON COLUMN "public"."store_backends"."container" IS '存储容器（根目录）';
COMMENT ON COLUMN "public"."store_backends"."endpoint" IS '服务端点';
COMMENT ON COLUMN "public"."store_backends"."port" IS '服务端口';
COMMENT ON COLUMN "public"."store_backends"."level" IS '存储级别';
COMMENT ON COLUMN "public"."store_backends"."is_active" IS '是否激活';
COMMENT ON COLUMN "public"."store_backends"."auth_type" IS '存储后端的访问认证方式      empty   userPassword    accessIdKey   accessToken';
COMMENT ON COLUMN "public"."store_backends"."auth_params_1" IS '认证参数1';
COMMENT ON COLUMN "public"."store_backends"."auth_params_2" IS '认证参数2';
COMMENT ON COLUMN "public"."store_backends"."auth_params_3" IS '认证参数3';
COMMENT ON COLUMN "public"."store_backends"."auth_params_4" IS '认证参数4';
COMMENT ON COLUMN "public"."store_backends"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."store_backends"."modify_time" IS '修改时间';
COMMENT ON COLUMN "public"."store_backends"."description" IS '描述信息';
COMMENT ON TABLE "public"."store_backends" IS '文件存储后端';

-- ----------------------------
-- Table structure for store_space
-- ----------------------------
DROP TABLE IF EXISTS "public"."store_space";
CREATE TABLE "public"."store_space" (
  "store_space_id" int8 NOT NULL,
  "creator_id" int8,
  "name" varchar(128) COLLATE "pg_catalog"."default",
  "size" int8,
  "is_deleted" bool,
  "create_time" timestamptz(6),
  "modify_time" timestamptz(6),
  "description" varchar(255) COLLATE "pg_catalog"."default",
  "is_default" bool
)
;
ALTER TABLE "public"."store_space" OWNER TO "test";
COMMENT ON COLUMN "public"."store_space"."store_space_id" IS '存储空间主键';
COMMENT ON COLUMN "public"."store_space"."creator_id" IS '创建用户';
COMMENT ON COLUMN "public"."store_space"."name" IS '存储空间名称';
COMMENT ON COLUMN "public"."store_space"."size" IS '存储空间大小';
COMMENT ON COLUMN "public"."store_space"."is_deleted" IS '是否删除';
COMMENT ON COLUMN "public"."store_space"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."store_space"."modify_time" IS '修改时间';
COMMENT ON COLUMN "public"."store_space"."description" IS '描述信息';
COMMENT ON COLUMN "public"."store_space"."is_default" IS '是否为默认存储空间';
COMMENT ON TABLE "public"."store_space" IS '数据存储空间';

-- ----------------------------
-- Table structure for upload_tasks
-- ----------------------------
DROP TABLE IF EXISTS "public"."upload_tasks";
CREATE TABLE "public"."upload_tasks" (
  "id" int8 NOT NULL,
  "store_id" int8,
  "creator_id" int8,
  "status" int4,
  "create_time" timestamptz(6),
  "modify_time" timestamptz(6),
  "finish_time" timestamptz(6),
  "is_deleted" bool
)
;
ALTER TABLE "public"."upload_tasks" OWNER TO "test";
COMMENT ON COLUMN "public"."upload_tasks"."id" IS '上传任务主键';
COMMENT ON COLUMN "public"."upload_tasks"."store_id" IS '存储空间';
COMMENT ON COLUMN "public"."upload_tasks"."creator_id" IS '创建用户';
COMMENT ON COLUMN "public"."upload_tasks"."status" IS '数据上传任务的状态   start   success  failed';
COMMENT ON COLUMN "public"."upload_tasks"."create_time" IS '上传任务创建时间';
COMMENT ON COLUMN "public"."upload_tasks"."modify_time" IS '上传任务修改时间';
COMMENT ON COLUMN "public"."upload_tasks"."finish_time" IS '上传任务结束时间';
COMMENT ON COLUMN "public"."upload_tasks"."is_deleted" IS '是否删除';
COMMENT ON TABLE "public"."upload_tasks" IS '文件上传任务';

-- ----------------------------
-- Indexes structure for table athena_file
-- ----------------------------
CREATE INDEX "files_creater_id_idx" ON "public"."athena_file" USING btree (
  "creater_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "files_source_id_idx" ON "public"."athena_file" USING btree (
  "source_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "files_store_id_idx" ON "public"."athena_file" USING btree (
  "store_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table athena_file
-- ----------------------------
ALTER TABLE "public"."athena_file" ADD CONSTRAINT "files_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table multipolar_store
-- ----------------------------
CREATE INDEX "multipolar_store_file_id_idx" ON "public"."multipolar_store" USING btree (
  "file_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table multipolar_store
-- ----------------------------
ALTER TABLE "public"."multipolar_store" ADD CONSTRAINT "multipolar_store_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table path_tree
-- ----------------------------
CREATE INDEX "path_tree_ancestor_id_idx" ON "public"."path_tree" USING btree (
  "ancestor_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "path_tree_descendant_id_idx" ON "public"."path_tree" USING btree (
  "descendant_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table path_tree
-- ----------------------------
ALTER TABLE "public"."path_tree" ADD CONSTRAINT "path_tree_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table store_backends
-- ----------------------------
ALTER TABLE "public"."store_backends" ADD CONSTRAINT "store_backends_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table store_space
-- ----------------------------
CREATE INDEX "store_spaces_creater_id_idx" ON "public"."store_space" USING btree (
  "creator_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table store_space
-- ----------------------------
ALTER TABLE "public"."store_space" ADD CONSTRAINT "store_spaces_pkey" PRIMARY KEY ("store_space_id");

-- ----------------------------
-- Indexes structure for table upload_tasks
-- ----------------------------
CREATE INDEX "upload_tasks_creater_id_idx" ON "public"."upload_tasks" USING btree (
  "creator_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "upload_tasks_store_id_idx" ON "public"."upload_tasks" USING btree (
  "store_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table upload_tasks
-- ----------------------------
ALTER TABLE "public"."upload_tasks" ADD CONSTRAINT "upload_tasks_pkey" PRIMARY KEY ("id");
