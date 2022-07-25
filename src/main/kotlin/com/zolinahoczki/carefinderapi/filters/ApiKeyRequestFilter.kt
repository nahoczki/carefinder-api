package com.zolinahoczki.carefinderapi.filters

import com.zolinahoczki.carefinderapi.entities.ApiKey
import com.zolinahoczki.carefinderapi.repositories.ApiKeyRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class ApiKeyRequestFilter(@Autowired private val apiKeyRepository: ApiKeyRepository) : GenericFilterBean() {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val path = req.requestURI
        if (!path.startsWith("/api") || "/apikeys" in path) {
            chain.doFilter(request, response)
            return
        }
        val key = if (req.getHeader("Key") == null) "" else req.getHeader("Key")
        LOG.info("Trying key: $key")
        val exists: Boolean = apiKeyRepository.existsByApiKey(key)
        if (exists) {
            chain.doFilter(request, response)
        } else {
            val resp = response as HttpServletResponse
            val error = "Invalid API KEY"
            resp.reset()
            resp.status = HttpServletResponse.SC_UNAUTHORIZED
            response.setContentLength(error.length)
            response.getWriter().write(error)
        }
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ApiKeyRequestFilter::class.java)
    }
}