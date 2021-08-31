import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:developer' as dev;

class AddressData {
  String? zipNo;
  String? roadAddr;
  String? roadAddr1;

  AddressData(dynamic data) {
    this.zipNo = data['zipNo'];
    this.roadAddr = data['roadAddr'];
    this.roadAddr1 = data['roadAddr1'];
  }
}

class Search extends StatefulWidget {
  @override
  State createState() {
    return SearchState();
  }
}

class SearchState extends State<Search> {
  List<dynamic> list = [];
  TextEditingController controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('주소 검색'),
      ),
      body: Column(
        children: [
          Padding(
            padding: EdgeInsets.fromLTRB(16, 0, 16, 0),
            child: Row(
              children: [
                Expanded(
                    child: TextField(
                      controller: controller,
                    )),
                ElevatedButton(
                    onPressed: () {
                      // get()
                      // post()
                      if (controller.text == '') return;

                      Map<String, String> params = {
                        'confmKey': 'devU01TX0FVVEgyMDIxMDgxNTEzMTc0NjExMTUyNDQ=',
                        'currentPage': '1',
                        'countPerPage': '100',
                        'keyword': controller.text,
                        'resultType': 'json',
                      };

                      http
                          .post(
                          Uri.parse(
                              'https://www.juso.go.kr/addrlink/addrLinkApi.do?'),
                          headers: {
                            'content-type':
                            'application/x-www-form-urlencoded',
                          },
                          body: params)
                          .then((response) {
                        var data = jsonDecode(response.body);
                        setState(() {
                          list = data['results']['juso'];
                        });
                      }).catchError((error) {});
                    },
                    child: Text('검색'))
              ],
            ),
          ),
          Expanded(
              child: ListView.separated(
            padding: EdgeInsets.fromLTRB(16, 12, 16, 12),
            itemBuilder: (BuildContext context, int index) {
              AddressData data = AddressData(list[index]);
              return Text('[${data.zipNo}] ${data.roadAddr}');
            },
            separatorBuilder: (BuildContext context, int index) => Divider(),
            itemCount: list.length,
          ))
        ],
      ),
    );
  }
}
