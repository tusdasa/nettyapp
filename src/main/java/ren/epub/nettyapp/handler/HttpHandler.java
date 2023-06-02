package ren.epub.nettyapp.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ren.epub.nettyapp.service.CallbackRecordService;
import ren.epub.nettyapp.service.impl.CallbackRecordServiceImpl;
import ren.epub.nettyapp.utils.ApplicationContextGetBeanHelper;


import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderValues.*;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private static final Logger Log = LoggerFactory.getLogger(HttpHandler.class);

    private final CallbackRecordService callbackRecordService = ApplicationContextGetBeanHelper.getBean(CallbackRecordServiceImpl.class);

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (Objects.nonNull(request)) {
            boolean keepAlive = HttpUtil.isKeepAlive(request);
            ChannelFuture f = null;
            HttpHeaders headers = request.headers();
            ByteBuf buf = request.content();
            if (buf!=null){
                String body = buf.toString(StandardCharsets.UTF_8);
                Log.info("request body => {}", body);
                Iterator<Map.Entry<String, String>> iterator = headers.iteratorAsString();
                StringBuilder stringBuilder = new StringBuilder();
                while (iterator.hasNext()){
                    Map.Entry<String, String> head = iterator.next();
                    stringBuilder.append(head.toString());
                    stringBuilder.append("  \n");
                    Log.info("request hader => {}", head.toString());
                }
                if (this.callbackRecordService != null) {
                    this.callbackRecordService.addCallbackRecord(stringBuilder.toString(),body);
                }
            }

            if (request.method() == HttpMethod.OPTIONS){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"OPTIONS\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if (request.method() == HttpMethod.GET){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"GET\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.HEAD){
                f = ctx.write(this.makeResponse(request, OK,
                        "",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.POST){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"POST\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.PUT){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"PUT\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.PATCH){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"PATCH\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.DELETE){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"DELETE\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.TRACE){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"TRACE\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else if(request.method() == HttpMethod.CONNECT){
                f = ctx.write(this.makeResponse(request, OK,
                        "{\"method\":\"CONNECT\",\"code\":200}",
                        AsciiString.cached("application/json; charset=utf-8")
                ));
            }else {
                ctx.close();
            }

            if (!keepAlive) {
                if (Objects.nonNull(f)){
                    f.addListener(ChannelFutureListener.CLOSE);
                }
            }

        }else {
            ctx.close();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Log.error("exception {}",cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
    private FullHttpResponse makeResponse(FullHttpRequest request, HttpResponseStatus status, String body, AsciiString contentType){
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), status,
                Unpooled.wrappedBuffer(body.getBytes(StandardCharsets.UTF_8)));
        response.headers()
                .set(CONTENT_TYPE, contentType)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());

        if (keepAlive) {
            if (!request.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(CONNECTION, KEEP_ALIVE);
            }
        } else {
            // Tell the client we're going to close the connection.
            response.headers().set(CONNECTION, CLOSE);
        }

        return response;
    }
}
