<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${rootPackage}.dao.${domainName}Dao">

    <select id="queryList" resultType="${rootPackage}.bo.${domainName}BO">
        SELECT
        *
        FROM ${table}
    </select>

    <insert id="save">
        INSERT INTO ${table}
        (
          <#list meta as m>
              ${r'`'}${m.name}${r'`'}<#if m_has_next>,</#if>
          </#list>
        ) values (
            <#list meta as m>
                ${r'#{'}${m.fieldName}${r'}'}<#if m_has_next>,</#if>
            </#list>
        )
    </insert>

    <update id="update" parameterType="${rootPackage}.entity.${domainName}Entity">
        update ${table}
        <set>
            <#list meta as m>
                <if test="${m.fieldName} != null and ${m.fieldName} != ''">
                    ${m.name} = ${r'#{'}${m.fieldName}${r'}'},
                </if>
            </#list>
        </set>
        version_n = version_n + 1
        where id = ${r'#{'}id${r'}'}
    </update>

    <delete id="delete">
        delete from ${table} where id = ${r'#{'}value${r'}'}
    </delete>

</mapper>