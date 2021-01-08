import React, { Component } from 'react';
import {
  Platform,
  NativeEventEmitter,
  StyleSheet,
  Text,
  View,
} from 'react-native';
import { CropImage, CropImageViewManager } from 'react-native-crop-image';

export default class App extends Component<{}> {
  state = {
    status: 'starting',
    message: '--',
    status1: 'Starting',
    promise: '--',
    threeDifferentTypesMethod: null,
  };
  // eventListener: any;
  async componentDidMount() {
    // event Listener
    const eventEmitter = new NativeEventEmitter(CropImageViewManager);
    this.eventListener = eventEmitter.addListener(
      'GET_CROPPED_IMAGE',
      (event) => {
        console.log('eventListener', event.event);
      }
    );
    // end event Listener
    CropImage.callbackMethod('Testing', 123, (message: any) => {
      this.setState({
        status: 'native callback received',
        message,
      });
    });

    // call promise function
    let promise = await CropImage.promiseMethod('Testing', 123);
    this.setState({
      status1: 'native promise received',
      promise,
    });
    // call promise function
    let threeDifferentTypesMethod = await CropImage.threeDifferentTypesMethod(
      'Testing',
      123,
      true
    );
    this.setState({
      threeDifferentTypesMethod,
    });
    if (Platform.OS === 'android') {
      let picimage = await CropImage.pickImage();
      console.warn(picimage);
    } else {
      CropImageViewManager.presentCropView();
    }
  }

  componentWillUnmount() {
    // this.eventListener.remove(); //Removes the listener
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>☆CropImage example☆</Text>
        <Text style={styles.instructions}>STATUS: {this.state.status}</Text>
        <Text style={styles.welcome}>☆NATIVE CALLBACK MESSAGE☆</Text>
        <Text style={styles.instructions}>{this.state.message}</Text>

        <Text style={styles.instructions}>STATUS: {this.state.status1}</Text>
        <Text style={styles.welcome}>☆NATIVE PROMISE MESSAGE☆</Text>
        <Text style={styles.instructions}>{this.state.promise}</Text>

        <Text style={styles.welcome}>☆Show Constent☆</Text>
        <Text style={styles.instructions}>
          LONG: {CropImage.LONG}, SHORT: {CropImage.LONG}
        </Text>
        <Text style={styles.welcome}>
          ☆Show Three Different Types Of Input Parameters☆
        </Text>
        <Text style={styles.instructions}>
          {JSON.stringify(this.state.threeDifferentTypesMethod)}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
