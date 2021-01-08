import { NativeModules } from 'react-native';

// type CropImageType = {
//   multiply(a: number, b: number): Promise<number>;
// };

const { CropImage } = NativeModules;
export { CropImage };

