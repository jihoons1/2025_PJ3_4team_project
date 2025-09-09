<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Best Meat</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="/css/common.css">
    <link rel="stylesheet" href="/css/index.css">
</head>

<body>
    <jsp:include page="/header.jsp"></jsp:include>

    <div id="container">
        <div class="banner_top">
        </div>

        <div class="main-content-grid">
            <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleAutoplaying" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleAutoplaying"
                    data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
            <div class="video-container">
                <iframe width="100%" height="100%"
                    src="https://www.youtube.com/embed/2l2oU858Yn4?autoplay=1&loop=1&playlist=2l2oU858Yn4&mute=1&start=60"
                    title="YouTube video player" frameborder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                    referrerpolicy="strict-origin-when-cross-origin" allowfullscreen>
                </iframe>
            </div>
        </div>

        <div class="map-section-grid">
            <div id="map"></div>
            <div id="sidebar">
                <div class="sidebar-placeholder">
                    지도에서 정육점을 클릭하여 상세 정보를 확인하세요.
                </div>
            </div>
        </div>

        <div class="banner_bot">
        </div>
    </div>

    <jsp:include page="/footer.jsp"></jsp:include>

    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="https://oapi.map.naver.com/openapi/v3/maps.js?ncpKeyId=7dgspubsn7"></script>
    <script type="text/javascript" src="/js/MarkerClustering.js"></script>

    <script src="/js/index.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>