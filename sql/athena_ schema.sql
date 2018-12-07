--
-- PostgreSQL database dump
--

-- Dumped from database version 11.0 (Debian 11.0-1.pgdg90+2)
-- Dumped by pg_dump version 11.0 (Debian 11.0-1.pgdg90+2)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: files; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.files (
    id character(32) NOT NULL,
    store_id character(32),
    creater_id character(32),
    store_space character varying(128),
    file_name character varying(255),
    file_size bigint,
    source_id character(32),
    source_type character(32),
    is_dir boolean,
    check_sum character varying(255),
    format character varying(32),
    status character varying(16),
    create_time timestamp(6) with time zone,
    modify_time timestamp(6) with time zone,
    description character varying(255)
);


ALTER TABLE public.files OWNER TO test;

--
-- Name: COLUMN files.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.id IS '文件主键';


--
-- Name: COLUMN files.store_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.store_id IS '存储空间';


--
-- Name: COLUMN files.creater_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.creater_id IS '创建用户';


--
-- Name: COLUMN files.store_space; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.store_space IS '文件所属存储空间名称';


--
-- Name: COLUMN files.file_name; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.file_name IS '文件名';


--
-- Name: COLUMN files.file_size; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.file_size IS '用户角度文件大小     单位 byte';


--
-- Name: COLUMN files.source_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.source_id IS '存储来源';


--
-- Name: COLUMN files.source_type; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.source_type IS '存储来源类型  upload   mount   system    task';


--
-- Name: COLUMN files.is_dir; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.is_dir IS '是否为目录';


--
-- Name: COLUMN files.check_sum; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.check_sum IS '文件的校验码';


--
-- Name: COLUMN files.format; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.format IS '文件格式      以文件扩展名为依据';


--
-- Name: COLUMN files.status; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.status IS '文件状态      new   uploading   available   failed   deleted';


--
-- Name: COLUMN files.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.create_time IS '创建时间';


--
-- Name: COLUMN files.modify_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.modify_time IS '修改时间';


--
-- Name: COLUMN files.description; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.files.description IS '描述信息';


--
-- Name: multipolar_store; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.multipolar_store (
    id integer NOT NULL,
    file_id character(32),
    status integer,
    level integer,
    is_active boolean,
    store_path text,
    store_size bigint,
    create_time timestamp(6) with time zone,
    modify_time timestamp(6) with time zone
);


ALTER TABLE public.multipolar_store OWNER TO test;

--
-- Name: TABLE multipolar_store; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON TABLE public.multipolar_store IS '文件的多级存储';


--
-- Name: COLUMN multipolar_store.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.id IS '多级存储主键';


--
-- Name: COLUMN multipolar_store.file_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.file_id IS '文件';


--
-- Name: COLUMN multipolar_store.status; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.status IS '实际存储在所属层级的状态      uploading, available, deleted';


--
-- Name: COLUMN multipolar_store.level; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.level IS '存储级别';


--
-- Name: COLUMN multipolar_store.is_active; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.is_active IS '是否是当前激活层级';


--
-- Name: COLUMN multipolar_store.store_path; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.store_path IS '实际存储路径';


--
-- Name: COLUMN multipolar_store.store_size; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.store_size IS '存储文件大小';


--
-- Name: COLUMN multipolar_store.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.create_time IS '创建时间';


--
-- Name: COLUMN multipolar_store.modify_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.multipolar_store.modify_time IS '修改时间';


--
-- Name: path_tree; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.path_tree (
    id character(32) NOT NULL,
    ancestor_id character(32),
    descendant_id character(32),
    depth boolean
);


ALTER TABLE public.path_tree OWNER TO test;

--
-- Name: TABLE path_tree; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON TABLE public.path_tree IS '文件树形关系 （采用闭包表）';


--
-- Name: COLUMN path_tree.ancestor_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.path_tree.ancestor_id IS '祖先文件';


--
-- Name: COLUMN path_tree.descendant_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.path_tree.descendant_id IS '子孙文件';


--
-- Name: COLUMN path_tree.depth; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.path_tree.depth IS '相对层级';


--
-- Name: store_backends; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.store_backends (
    id character(32) NOT NULL,
    name character varying(128),
    protocol character varying(128),
    container character varying(255),
    endpoint character varying(255),
    port integer,
    level integer,
    is_active boolean,
    auth_type integer,
    auth_params_1 character varying(255),
    auth_params_2 character varying(255),
    auth_params_3 character varying(255),
    auth_params_4 character varying(255),
    create_time timestamp(6) with time zone,
    modify_time timestamp(6) with time zone,
    description character varying(255)
);


ALTER TABLE public.store_backends OWNER TO test;

--
-- Name: TABLE store_backends; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON TABLE public.store_backends IS '文件存储后端';


--
-- Name: COLUMN store_backends.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.id IS '存储后端主键';


--
-- Name: COLUMN store_backends.name; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.name IS '存储后端名称';


--
-- Name: COLUMN store_backends.protocol; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.protocol IS '存储协议';


--
-- Name: COLUMN store_backends.container; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.container IS '存储容器（根目录）';


--
-- Name: COLUMN store_backends.endpoint; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.endpoint IS '服务端点';


--
-- Name: COLUMN store_backends.port; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.port IS '服务端口';


--
-- Name: COLUMN store_backends.level; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.level IS '存储级别';


--
-- Name: COLUMN store_backends.is_active; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.is_active IS '是否激活';


--
-- Name: COLUMN store_backends.auth_type; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.auth_type IS '存储后端的访问认证方式      empty   userPassword    accessIdKey   accessToken';


--
-- Name: COLUMN store_backends.auth_params_1; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.auth_params_1 IS '认证参数1';


--
-- Name: COLUMN store_backends.auth_params_2; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.auth_params_2 IS '认证参数2';


--
-- Name: COLUMN store_backends.auth_params_3; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.auth_params_3 IS '认证参数3';


--
-- Name: COLUMN store_backends.auth_params_4; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.auth_params_4 IS '认证参数4';


--
-- Name: COLUMN store_backends.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.create_time IS '创建时间';


--
-- Name: COLUMN store_backends.modify_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.modify_time IS '修改时间';


--
-- Name: COLUMN store_backends.description; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_backends.description IS '描述信息';


--
-- Name: store_spaces; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.store_spaces (
    id character(32) NOT NULL,
    creater_id character(32),
    store_space character varying(128),
    store_size bigint,
    is_deleted boolean,
    create_time timestamp(6) with time zone,
    modify_time timestamp(6) with time zone,
    description character varying(255)
);


ALTER TABLE public.store_spaces OWNER TO test;

--
-- Name: TABLE store_spaces; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON TABLE public.store_spaces IS '数据存储空间';


--
-- Name: COLUMN store_spaces.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.id IS '存储空间主键';


--
-- Name: COLUMN store_spaces.creater_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.creater_id IS '创建用户';


--
-- Name: COLUMN store_spaces.store_space; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.store_space IS '存储空间名称';


--
-- Name: COLUMN store_spaces.store_size; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.store_size IS '存储空间大小';


--
-- Name: COLUMN store_spaces.is_deleted; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.is_deleted IS '是否删除';


--
-- Name: COLUMN store_spaces.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.create_time IS '创建时间';


--
-- Name: COLUMN store_spaces.modify_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.modify_time IS '修改时间';


--
-- Name: COLUMN store_spaces.description; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.store_spaces.description IS '描述信息';


--
-- Name: upload_tasks; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.upload_tasks (
    id character(32) NOT NULL,
    store_id character(32),
    creater_id character(32),
    status integer,
    create_time timestamp(6) with time zone,
    modify_time timestamp(6) with time zone,
    finish_time timestamp(6) with time zone,
    is_deleted boolean
);


ALTER TABLE public.upload_tasks OWNER TO test;

--
-- Name: TABLE upload_tasks; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON TABLE public.upload_tasks IS '文件上传任务';


--
-- Name: COLUMN upload_tasks.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.id IS '上传任务主键';


--
-- Name: COLUMN upload_tasks.store_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.store_id IS '存储空间';


--
-- Name: COLUMN upload_tasks.creater_id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.creater_id IS '创建用户';


--
-- Name: COLUMN upload_tasks.status; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.status IS '数据上传任务的状态   start   success  failed';


--
-- Name: COLUMN upload_tasks.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.create_time IS '上传任务创建时间';


--
-- Name: COLUMN upload_tasks.modify_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.modify_time IS '上传任务修改时间';


--
-- Name: COLUMN upload_tasks.finish_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.finish_time IS '上传任务结束时间';


--
-- Name: COLUMN upload_tasks.is_deleted; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.upload_tasks.is_deleted IS '是否删除';


--
-- Name: users; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.users (
    id character(32) NOT NULL,
    password character varying(128),
    email character varying(128),
    mobile character varying(32),
    profile character varying(255),
    create_time timestamp(6) with time zone,
    username character varying(64),
    nickname character varying(64)
);


ALTER TABLE public.users OWNER TO test;

--
-- Name: COLUMN users.id; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.id IS '用户主键';


--
-- Name: COLUMN users.password; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.password IS '用户密码';


--
-- Name: COLUMN users.email; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.email IS '邮箱';


--
-- Name: COLUMN users.mobile; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.mobile IS '手机号';


--
-- Name: COLUMN users.profile; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.profile IS '个人简介';


--
-- Name: COLUMN users.create_time; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.create_time IS '创建时间';


--
-- Name: COLUMN users.username; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.username IS '用户名称';


--
-- Name: COLUMN users.nickname; Type: COMMENT; Schema: public; Owner: test
--

COMMENT ON COLUMN public.users.nickname IS '用户昵称';

--
-- Name: files files_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.files
    ADD CONSTRAINT files_pkey PRIMARY KEY (id);


--
-- Name: multipolar_store multipolar_store_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.multipolar_store
    ADD CONSTRAINT multipolar_store_pkey PRIMARY KEY (id);


--
-- Name: path_tree path_tree_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.path_tree
    ADD CONSTRAINT path_tree_pkey PRIMARY KEY (id);


--
-- Name: store_backends store_backends_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.store_backends
    ADD CONSTRAINT store_backends_pkey PRIMARY KEY (id);


--
-- Name: store_spaces store_spaces_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.store_spaces
    ADD CONSTRAINT store_spaces_pkey PRIMARY KEY (id);


--
-- Name: upload_tasks upload_tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.upload_tasks
    ADD CONSTRAINT upload_tasks_pkey PRIMARY KEY (id);


--
-- Name: users user_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT user_pkey PRIMARY KEY (id);


--
-- Name: files_creater_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX files_creater_id_idx ON public.files USING btree (creater_id);


--
-- Name: files_source_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX files_source_id_idx ON public.files USING btree (source_id);


--
-- Name: files_store_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX files_store_id_idx ON public.files USING btree (store_id);


--
-- Name: multipolar_store_file_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX multipolar_store_file_id_idx ON public.multipolar_store USING btree (file_id);


--
-- Name: path_tree_ancestor_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX path_tree_ancestor_id_idx ON public.path_tree USING btree (ancestor_id);


--
-- Name: path_tree_descendant_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX path_tree_descendant_id_idx ON public.path_tree USING btree (descendant_id);


--
-- Name: store_spaces_creater_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX store_spaces_creater_id_idx ON public.store_spaces USING btree (creater_id);


--
-- Name: upload_tasks_creater_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX upload_tasks_creater_id_idx ON public.upload_tasks USING btree (creater_id);


--
-- Name: upload_tasks_store_id_idx; Type: INDEX; Schema: public; Owner: test
--

CREATE INDEX upload_tasks_store_id_idx ON public.upload_tasks USING btree (store_id);


--
-- PostgreSQL database dump complete
--

