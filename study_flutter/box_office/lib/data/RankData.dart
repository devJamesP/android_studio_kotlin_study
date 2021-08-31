class RankData {
  late int rank;
  late int rankInten;
  late String rankOldAndNew;
  late String movieNm;
  late bool isNew = false;

  RankData(dynamic raw) {
    this.rank = int.parse(raw['rank']);
    this.rankInten = int.parse(raw['rankInten']);
    this.rankOldAndNew = raw['rankOldAndNew'];
    this.isNew = rankOldAndNew == 'NEW';
    this.movieNm = raw['movieNm'];
    if (this.movieNm.length > 14) {
      this.movieNm = this.movieNm.substring(0, 14) + '..';
    }
  }
}