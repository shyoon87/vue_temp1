<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.ysh.fenu.mapper.ExampleMapper">

    <select id="selectTableList" resultType="Map">
        /* example.selectTableList */
        select  id
                , timestamp
                , author
                , importance
                , status
                , title
                , type
                , pageviews
                , reviewer
        from tb_exm_table
        order by id
        limit #{ offset }, #{ limit }
    </select>

    <select id="selectTableListTotal" resultType="int">
        /* example.selectTableListTotal */
        select  count(*)
        from tb_exm_table
    </select>

</mapper>