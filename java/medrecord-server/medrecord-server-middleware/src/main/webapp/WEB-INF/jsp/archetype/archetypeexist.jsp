<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../header.jsp" %>

<script type="text/javascript" charset="utf-8">
  $(document).ready(function() {
    $.get('v1/test/getarchid', function(data) {
      $("#archid").val(data);
    });
  });

  function isArchtype() {
    $("#result").html("Loading data...");
    setUserId(localStorage.userid);
    setSystemId(localStorage.systemid);
    archetypeExist($("#archid").val(), setResult);
  }
</script>
</head>

<body>
<h2>Call web API function 'archetypeExist'</h2>

<div class="docpanel">
<table xmlns="http://www.w3.org/1999/xhtml" cellspacing="0" class="sites-layout-name-one-column sites-layout-hbox">
  <tbody>
  <tr>
    <td class="sites-layout-tile sites-tile-name-content-1">
      <div dir="ltr"><a name="top"></a>

        <div dir="ltr">
          <table border="1" bordercolor="#888888" cellpadding="5" cellspacing="0"
                 style="border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse" width="100%">
            <tbody>
            <tr>
              <td align="left" bgcolor="#cccccc" style="height:20px" valign="middle">
                <b>Description</b>
              </td>
            </tr>
            <tr>
              <td style="height:18px">
                <div class="sites-codeblock sites-codesnippet-block"><code>

                  function archetypeExist(archid, callback)

                </code></div>
                Returns true if an archetype is already stored in de open EHR kernel.<br><br>

                <p style="margin:0px 0px 0px 0px;font:13.0px Arial;color:#444444"><font face="arial, sans-serif"> Can
                  also be directly called as GET (REST) request: </font>
                </p><font face="arial, sans-serif">
                <p style="margin:0px 0px 0px 0px;font:13.0px Monaco;color:#016001">
                  http://&lt;middlewareserver&gt;/middleware/archetypeexist </p>

                <p style="margin:0px 0px 0px 0px;font:13.0px Arial;color:#444444">
                  Parameters: &nbsp; &nbsp;<code>archid</code>
                </p>

                <p style="margin:0px 0px 0px 0px;font:13.0px Monaco;color:#016001">
                  <span style="font:13.0px Arial;color:#444444">
                    Return:&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;</span> the data object in JSON format
                </p></font></td>
            </tr>
            </tbody>
          </table>
          <br>
          <table border="1" bordercolor="#888888" cellpadding="5" cellspacing="0"
                 style="border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse" width="100%">
            <tbody>
            <tr>
              <td bgcolor="#cccccc" style="vertical-align:top;width:537px;height:32px">
                <b>Parameters</b>
              </td>
            </tr>
            <tr>
              <td style="vertical-align:top;width:537px;height:18px">
                archid &nbsp; &nbsp;The id of the archetype<br>callback A callback function returning an object with the
                data and an error code
              </td>
            </tr>
            </tbody>
          </table>
          <br>
          <table border="1" bordercolor="#888888" cellpadding="5" cellspacing="0"
                 style="border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse" width="100%">
            <tbody>
            <tr>
              <td bgcolor="#cccccc" style="width:537px;height:32px">
                <b>Return values</b>
              </td>
            </tr>
            <tr>
              <td style="width:537px;height:18px">
                The callback returns an object with the data and an error code<br>

                <div class="sites-codeblock sites-codesnippet-block">
                  <code>object = {</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>archid: <span>&nbsp;&nbsp; </span>par,
                  &nbsp;// Request parameter</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>result: &nbsp;<span>&nbsp;&nbsp;</span>data,
                  // The result data</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>errorcode: code &nbsp;// The error
                  code</code><br><code>}</code>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
          <br>
          <table border="1" bordercolor="#888888" cellpadding="5" cellspacing="0"
                 style="border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse" width="100%">
            <tbody>
            <tr>
              <td bgcolor="#cccccc" style="width:537px;height:32px">
                <b>Examples</b>
              </td>
            </tr>
            <tr>
              <td style="width:537px;height:18px">
                <div class="sites-codeblock sites-codesnippet-block">
                  <code>function getArchtype()</code><br><code> {</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>$("#result").html("Loading
                  data...");</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>archetypeExist($("#archid").val(),
                  setResult);</code><br><code> } /* getArchtype */</code><br><br><code> function
                  setResult(data)</code><br><code> {</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>if (data.errorcode
                  != 0)</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>{</code><br><code>
                  <span>&nbsp;&nbsp; &nbsp;<span>&nbsp;&nbsp; &nbsp;</span></span>// Handle the error</code><br><code>
                  <span>&nbsp;&nbsp; &nbsp;</span>}</code><br><code>
                  <span>&nbsp;&nbsp; &nbsp;</span>else</code><br><code>
                  <span>&nbsp;&nbsp; &nbsp;</span>{</code><br><code>
                  <span>&nbsp;&nbsp; &nbsp;<span>&nbsp;&nbsp; &nbsp;</span></span>$("#result").html("Result of call:&lt;pre&gt;"
                  + data.result + "&lt;/pre&gt;");</code><br><code> <span>&nbsp;&nbsp; &nbsp;</span>}</code><br><code> }
                  /* setResult */</code>
                </div>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </td>
  </tr>
  </tbody>
</table>
</div>
<div>
  Archetype Id <input type="text" name="archid" id="archid" value="" size="50"/>
  <p></p>
  <input type="submit" value="Get Result" name="GetResult" onclick="isArchtype();"/>
</div>

<%@include file="../footer.jsp" %>
