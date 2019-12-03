<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.ysh.fenu.mapper.LoginMapper">

    <select id="selectAccToken_web" resultType="Map">
         /* login.selectAccToken_web */
        SELECT  user_id
		        , name
                , role
                , email
        FROM tb_user u
        INNER JOIN (
                    SELECT user_web_id
			        FROM tb_user_web
			        WHERE login_id = #{ userId }
					    AND login_pw = #{ password }
		        )uw
                ON u.user_web_id = uw.user_web_id
    </select>

    <select id="selectTokenUser" parameterType="com.ysh.fenu.comm.security.JwtUser" resultType="Map">
        /* login.selectTokenUser */
        select  user_id
                , name
                , role
                , email
        from tb_user
        where   user_id = #{ userId }
                AND name = #{ name }
    </select>

    <select id="selectUserInfo" resultType="Map">
        /* login.selectUserInfo */
        WITH gb AS (
                    SELECT  IFNULL(SUM(good), 0) AS good
                            , IFNULL(SUM(bad), 0) AS bad
                    FROM (
                            SELECT food_id
                    	    FROM tb_food
                            WHERE user_id = #{ userId }
                                AND use_yn = 'Y') f
                    INNER JOIN tb_food_apprs fa
                    		ON f.food_id = fa.food_id
        )
        SELECT  name
                , `desc`
                , (SELECT good FROM gb) AS good
                , (SELECT bad FROM gb) AS bad
        from tb_user
        where   user_id = #{ userId }
    </select>

    <select id="selectUserInfoFromMenuId" resultType="Map">
        /* login.selectUserInfoFromMenuId*/
        WITH user AS (
        			SELECT u.user_id
        					, u.name
                            , u.`desc`
                            , m.title
                            , m.img
                    FROM tb_user u
        			INNER JOIN (
        			                SELECT  user_id
        			                        , title
        			                        , img
        			                FROM fenu.tb_menu
        			                where menu_id = #{ menu_id }
        			                LIMIT 1 ) m
        					ON u.user_id = m.user_id
        ),
        gb as (
        	SELECT  IFNULL(SUM(good), 0) AS good
        			, IFNULL(SUM(bad), 0) AS bad
        	FROM (
        			SELECT food_id
        			FROM tb_food
        			WHERE menu_id = #{ menu_id }
        				AND use_yn = 'Y') f
        	INNER JOIN tb_food_apprs fa
        			ON f.food_id = fa.food_id
        )
        SELECT user_id
        		, name
                , `desc`
                , title
                , img
                , (SELECT img FROM tb_menu WHERE menu_id = #{ menu_id } LIMIT 1) AS img
                , (SELECT good FROM gb) AS good
                , (SELECT bad FROM gb) AS bad
        FROM user
    </select>
</mapper>