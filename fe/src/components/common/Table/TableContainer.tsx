// import { useTheme } from '@emotion/react';
// import { useState } from 'react';
// import { Button } from '../Button';
// import { ReactComponent as XSquare } from '@assets/icons/xSquare.svg';
// import { ReactComponent as Plus } from '@assets/icons/plus.svg';
// import { ReactComponent as Edit } from '@assets/icons/edit.svg';
// import { randomColorGenerator } from '@utils/generateRandomColorCode';
// import { TableBody } from './TableBody';

// type Props = {
//   typeVariant: 'add' | 'edit';
//   tableVariant: 'label' | 'milestone';
//   header: React.ReactNode;
//   onAddTableClose?: () => void;
// };

// export const TableContainer: React.FC<Props> = ({
//   typeVariant,
//   tableVariant,
//   header,
//   onAddTableClose,
// }) => {
//   const theme = useTheme() as any;
//   const [nameInput, setNameInput] = useState<string>('');
//   const [descriptionInput, setDescriptionInput] = useState<string>('');
//   const [colorCodeInput, setColorCodeInput] = useState<string>(
//     randomColorGenerator(),
//   );
//   const [selectedTextColor, setSelectedTextColor] = useState<number | null>(
//     null,
//   );

//   const [isPanelOpen, setIsPanelOpen] = useState<boolean>(false);

//   const onNameChange = (value: string) => {
//     if (value.length > 21) return;
//     setNameInput(value);
//   };

//   const onDescriptionChange = (value: string) => {
//     setDescriptionInput(value);
//   };

//   const onPanelOpen = () => {
//     setIsPanelOpen(true);
//   };

//   const onPanelClose = () => {
//     setIsPanelOpen(false);
//   };

//   const onSelectTextColor = (id: number) => {
//     setSelectedTextColor(id);
//     onPanelClose();
//   };

//   const onColorCodeChange = (value: string) => {
//     setColorCodeInput(value);
//   };

//   const onColorCodeRandom = () => {
//     const colorCode = randomColorGenerator();

//     setColorCodeInput(colorCode);
//   };

//   const onSubmitAdd = async (path: string) => {
//     const body = {
//       name: nameInput,
//       textColor: textColor,
//       backgroundColor: colorCodeInput,
//       description: descriptionInput || '',
//     };

//     try {
//       const response = await fetch(
//         `${import.meta.env.VITE_APP_BASE_URL}/${path},`,
//         {
//           method: 'POST',
//           headers: {
//             'Content-Type': 'application/json',
//           },
//           body: JSON.stringify(body),
//         },
//       );

//       if (!response.ok) {
//         throw new Error('Network response was not ok');
//       }
//       const data = await response.json();
//       console.log(data);
//     } catch (error) {
//       console.error('There was a problem with the fetch operation:');
//     }
//   };

//   const isDataMissing = () => {
//     return !nameInput || !colorCodeInput || !selectedTextColor;
//   };

//   const isColorCodeError =
//     !colorCodeInput ||
//     colorCodeInput[0] !== '#' ||
//     (colorCodeInput.length !== 7 && colorCodeInput.length !== 4) ||
//     !/^#[0-9A-Fa-f]{3,6}$/.test(colorCodeInput);

//   const fillColor = colorCodeInput ? colorCodeInput : randomColorGenerator();

//   const displayedTextColor =
//     selectedTextColor === null
//       ? '텍스트 색상'
//       : selectedTextColor === 1
//       ? '밝은 색'
//       : '어두운 색';

//   const isNameLengthError = nameInput.length > 20;

//   const textColor = selectedTextColor === 1 ? 'light' : 'dark';

//   const addTypeStyle = {
//     border: `${theme.border.default}  ${theme.neutral.border.default}`,
//     borderRadius: theme.radius.l,
//     padding: '32px',
//   };

//   return (
//     <div
//       css={{
//         width: '100%',
//         boxSizing: 'border-box',
//         height: 'fit-content',
//         display: 'flex',
//         backgroundColor: theme.neutral.surface.strong,
//         flexDirection: 'column',
//         gap: '24px',
//         ...(typeVariant === 'add' ? addTypeStyle : {}),
//       }}
//     >
//       {header}
//       <TableBody
//         typeVariant={typeVariant}
//         tableVariant={tableVariant}
//         nameInput={nameInput}
//         descriptionInput={descriptionInput}
//         colorCodeInput={colorCodeInput}
//         selectedTextColor={selectedTextColor}
//         fillColor={fillColor}
//         textColor={textColor}
//         displayedTextColor={displayedTextColor}
//         isNameLengthError={isNameLengthError}
//         isColorCodeError={isColorCodeError}
//         isPanelOpen={isPanelOpen}
//         onNameChange={onNameChange}
//         onDescriptionChange={onDescriptionChange}
//         onPanelOpen={onPanelOpen}
//         onPanelClose={onPanelClose}
//         onSelectTextColor={onSelectTextColor}
//         onColorCodeChange={onColorCodeChange}
//         onColorCodeRandom={onColorCodeRandom}
//       />

//       <div
//         css={{
//           display: 'flex',
//           gap: '16px',
//           justifyContent: 'flex-end',
//         }}
//       >
//         <Button
//           typeVariant="outline"
//           size="S"
//           css={{
//             color: theme.brand.text.weak,
//           }}
//           onClick={onAddTableClose}
//         >
//           <XSquare stroke={theme.brand.text.weak} />
//           취소
//         </Button>
//         <Button
//           typeVariant="contained"
//           size="S"
//           css={{
//             color: theme.brand.text.default,
//           }}
//           disabled={isDataMissing() || isColorCodeError}
//           onClick={() => {
//             onSubmitAdd('labels');
//             onPanelClose();
//           }}
//         >
//           {typeVariant === 'edit' ? (
//             <Edit stroke={theme.brand.text.default} />
//           ) : (
//             <Plus stroke={theme.brand.text.default} />
//           )}
//           {typeVariant === 'edit' ? '편집 완료' : '완료'}
//         </Button>
//       </div>
//     </div>
//   );
// };
