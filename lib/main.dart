import 'dart:async';

import 'package:eventchannel/listener/listener.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      title: "Compass App",
      home: _EventChannel(),
    );
  }
}

class _EventChannel extends StatefulWidget {
  const _EventChannel({Key? key}) : super(key: key);

  @override
  State<_EventChannel> createState() => _EventChannelState();
}

class _EventChannelState extends State<_EventChannel> {
  late StreamSubscription _streamSubscription;
  late List<double> sensorValues;

  @override
  initState() {
    sensorValues = <double>[];
    _streamSubscription = eventData.listen((event) {
      setState(() {
        sensorValues = <double>[event.x, event.y, event.z];
        debugPrint(sensorValues.toString());
      });
    });
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Event App"),
      ),
      body: Center(
        child: Image.asset("assets/compass.png"),
      ),
    );
  }
}
