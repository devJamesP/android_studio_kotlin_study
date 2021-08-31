import 'package:box_office/data/RankData.dart';
import 'package:flutter/material.dart';


class Item extends StatelessWidget {
  late RankData data;
  Item( this.data );

  TextStyle rankStyle = TextStyle(fontSize: 32);
  TextStyle defaultStyle = TextStyle(
    fontSize: 20,
  );
  TextStyle subInfo = TextStyle(
    fontSize: 14,
  );

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Padding(
            padding: EdgeInsets.all(16),
            child: Text(data.rank.toString(), style: rankStyle,)
        ),
        Text('${(data.rankInten < 0 ? '🔻' : '🔺')}${data.rankInten}' , style: subInfo,),
        Padding(
          padding: EdgeInsets.fromLTRB(16, 16, 8, 16),
          child: Text(data.movieNm, style: defaultStyle,),
        ),
        Text(data.isNew ? '🆕' : '', style: subInfo,),
      ],
    );
  }
}
