drawScoreTable();
var s = "";
var Game = "";
var r = "";
var Rating = "";


function valueFromSelect() {
	s = document.getElementById("hraVyber");
	Game = s.options[s.selectedIndex].text;	}

function valueFromSelectRating() {
	r = document.getElementById("ratingVyber");
	Rating = r.options[r.selectedIndex].text;	}

function drawRating(){
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/rating/" + Game,
		dataType : "json"
	}

	).done(function(receivedData) {
		var ratingTemplate = $("#tmplRating").html();
		var htmlCode = Mustache.render(ratingTemplate, receivedData);
		$("#textRating").html(htmlCode);
	}).fail(function() {
		$("#textRating").html("<p>Nepodarilo sa poslať dáta</p>");
	});
}

$("#SaveRating").click(function() {
	Rating = parseInt($("#ratingVyber").val().trim())
	var userName = $("#userName3").val();
	
	console.log(userName);
	console.log(Rating);
	
//	if( document.getElementById('userName').value === '' ){
//	      window.alert("Zlý vstup v kolónke užívateľské meno !");
//	      return;
//	    }

	
	var data2send = {
			 username: userName,
		        game: Game,
		        value: Rating
	}
	
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/rest/rating",
		contentType : "application/json",
		data: JSON.stringify(data2send)
	}

	).done(function() {
		drawRating();
	}).fail(function() {
		window.alert("Nepodarilo sa poslať dáta!");
	});
});


function drawCommentTable(){
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/comment/" + Game,
		dataType : "json"
	}

	).done(function(receivedData) {
		var commentTemplate = $("#tmplComment").html();
		var htmlCode = Mustache.render(commentTemplate, receivedData);
		$("#tableComment").html(htmlCode);
	}).fail(function() {
		$("#tableComment").html("<p>Nepodarilo sa poslať dáta</p>");
	});
}

$("#SaveComment").click(function() {
	var comment = $("#newComment").val();
	var userName = $("#userName").val();
	
	console.log(userName);
	console.log(comment);
	
	
	if( document.getElementById('newComment').value === '' ){
	      window.alert("Nezadal si žiaden komentár !");
	      return;
	    }
	
	if( document.getElementById('userName').value === '' ){
	      window.alert("Zlý vstup v kolónke užívateľské meno !");
	      return;
	    }

	
	var data2send = {
			 username: userName,
		        game: Game,
		        content: comment
	}
	
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/rest/comment",
		contentType : "application/json",
		data: JSON.stringify(data2send)
	}

	).done(function() {
		drawCommentTable();
	}).fail(function() {
		window.alert("Nepodarilo sa poslať dáta!");
	});
});

function drawScoreTable(){
	$.ajax({
		type : "GET",
		url : "http://localhost:8080/rest/score/" + Game,
		dataType : "json"
	}

	).done(function(receivedData) {
		var scoreTemplate = $("#tmplScore").html();
		var htmlCode = Mustache.render(scoreTemplate, receivedData);
		$("#tableScore").html(htmlCode);
	}).fail(function() {
		$("#tableScore").html("<p>Nepodarilo sa poslať dáta</p>");
	});
}

$("#SaveScore").click(function() {
	var score = parseInt($("#newScore").val().trim());
	var userName = $("#userName").val();
	

	console.log(Game);
	console.log(userName);
	console.log(score);
	
	
	if (isNaN(score)) {
		window.alert("Zlý vstup v kolónke skóre!");
		return;
	}
	
	if( document.getElementById('userName').value === '' ){
	      window.alert("Zlý vstup v kolónke užívateľské meno !");
	      return;
	    }

	
	var data2send = {
			 username: userName,
		        game: Game,
		        value: score
	}
	
	$.ajax({
		type : "POST",
		url : "http://localhost:8080/rest/score",
		contentType : "application/json",
		data: JSON.stringify(data2send)
	}

	).done(function() {
		drawScoreTable();
	}).fail(function() {
		window.alert("Nepodarilo sa poslať dáta!");
	});
}

);
