import {useState} from "react";
import {styled} from "styled-components";
import {Button} from "../../components/Button";
import {Icon} from "../../components/Icon";
import {InformationTag} from "../../components/InformationTag";
import {TextInput} from "../../components/TextInput";
import {DropdownContainer} from "../../components/dropdown/DropdownContainer";
import {LabelData} from "./Label";

const colorDictionary = {
  LIGHT: "밝은 색",
  DARK: "어두운 색",
  "밝은 색": "LIGHT",
  "어두운 색": "DARK",
};

type LabelEditorProps = {
  onClickClose: () => void;
} & ({ type: "add"; label?: never } | { type: "edit"; label: LabelData });

export function LabelEditor({onClickClose, type, label}: LabelEditorProps) {
  const isEditMode = type === "edit" && label;

  const [selectedFontColor, setSelectedFontColor] = useState<string>(
      isEditMode ? colorDictionary[label.color] : "밝은 색",
  );
  const [background, setBackground] = useState(
      isEditMode ? label.background : "#000000",
  );
  const [color, setColor] = useState<"LIGHT" | "DARK">(
      isEditMode ? label.color : "LIGHT",
  );
  const [labelName, setLabelName] = useState(isEditMode ? label.name : "");
  const [labelDescription, setLabelDescription] = useState(
      isEditMode && label.description ? label.description : "",
  );
  const [isColorValid, setIsColorValid] = useState(true);
  const [isFocus, setIsFocus] = useState(false);
  const [fontColorOptions, setFontColorOptions] = useState([
    {
      name: "밝은 색",
      profile: "",
      selected: isEditMode ? colorDictionary[label.color] === "밝은 색" : true,
      onClick: () => {
        updateOptions("밝은 색");
      },
    },
    {
      name: "어두운 색",
      profile: "",
      selected: isEditMode
          ? colorDictionary[label.color] === "어두운 색"
          : false,
      onClick: () => {
        updateOptions("어두운 색");
      },
    },
  ]);

  const isButtonDisabled = isEditMode
      ? !isColorValid ||
      (labelName === label.name &&
          background === label.background &&
          color === label.color) ||
      labelName === ""
      : !isColorValid || labelName === "";

  const updateOptions = (selectedName: string) => {
    const fontColor = selectedName === "밝은 색" ? "LIGHT" : "DARK";

    setColor(fontColor);
    setSelectedFontColor(selectedName);
    setFontColorOptions(
        fontColorOptions.map((option) => {
          return {...option, selected: option.name === selectedName};
        }),
    );
  };

  const onChangeBackground = (event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;

    if (!value.startsWith("#")) {
      value = "#" + value;
    }

    // hex값 체크
    const isValidColor = /^#([0-9A-F]{3}){1,2}$/i.test(value);

    setBackground(value);
    setIsColorValid(isValidColor);
  };

  const setRandomBackground = () => {
    const letters = "0123456789ABCDEF";
    let color = "#";

    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * letters.length)];
    }
    setBackground(color);
  };

  const onChangeName = (event: React.ChangeEvent<HTMLInputElement>) => {
    const name = event.target.value;

    setLabelName(name.trim());
  };

  const onChangeDescription = (event: React.ChangeEvent<HTMLInputElement>) => {
    const description = event.target.value;

    setLabelDescription(description);
  };

  const submit = () => {
    const obj = {
      name: labelName,
      description: labelDescription,
      background: background,
      fontColor: color,
    };

    console.log(obj);
  };

  return (
      <Div>
        <Title>{type === "add" ? "새로운 레이블 추가" : "레이블 편집"}</Title>
        <AddTable>
          <Preview>
            <InformationTag
                value={labelName === "" ? "label" : labelName}
                size="S"
                fill={background}
                fontColor={color}
            />
          </Preview>
          <InputWrapper>
            <TextInput
                width={904}
                size="S"
                label="이름"
                placeholder="레이블의 이름을 입력하세요"
                value={labelName}
                fixLabel
                onChange={onChangeName}
            />
            <TextInput
                width={904}
                size="S"
                label="설명(선택)"
                placeholder="레이블에 대한 설명을 입력하세요"
                fixLabel
                value={labelDescription}
                onChange={onChangeDescription}
            />
            <ColorSelector>
              <BackgroundSelector $isFocus={isFocus} $isColorValid={isColorValid}>
                <span>배경 색상</span>
                <TextInput
                    size="S"
                    value={background}
                    borderNone
                    onFocus={() => setIsFocus(true)}
                    onBlur={() => setIsFocus(false)}
                    onChange={onChangeBackground}
                />
                <button onClick={setRandomBackground}>
                  <Icon name="refreshCcw" fill="#4E4B66" stroke="#4E4B66"/>
                </button>
              </BackgroundSelector>
              <DropdownContainer
                  name={selectedFontColor}
                  optionTitle={"텍스트 색상"}
                  options={fontColorOptions}
                  alignment="Left"
              />
            </ColorSelector>
          </InputWrapper>
        </AddTable>
        <Buttons>
          <Button
              size="S"
              icon="xSquare"
              buttonType="Outline"
              onClick={onClickClose}
          >
            취소
          </Button>
          <Button
              size="S"
              icon="plus"
              buttonType="Container"
              onClick={submit}
              disabled={isButtonDisabled}
          >
            완료
          </Button>
        </Buttons>
      </Div>
  );
}

const Div = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 337px;
  align-self: stretch;
  box-sizing: border-box;
  padding: 32px;
  gap: 24px;
`;

const Title = styled.div`
  font: ${({theme}) => theme.font.displayBold20};
  color: ${({theme}) => theme.color.neutralTextStrong};
`;

const AddTable = styled.div`
  width: 100%;
  height: 153px;
  display: flex;
  gap: 24px;
`;

const Preview = styled.div`
  display: flex;
  width: 288px;
  height: 153px;
  justify-content: center;
  align-items: center;
  flex-shrink: 0;
  border-radius: ${({theme}) => theme.radius.large};
  border: 1px solid ${({theme}) => theme.color.neutralBorderDefault};
`;

const InputWrapper = styled.div`
  width: 100%;
  height: 153px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const ColorSelector = styled.div`
  display: flex;
  gap: 24px;
`;

const BackgroundSelector = styled.div<{
  $isFocus: boolean;
  $isColorValid: boolean;
}>`
  display: flex;
  height: 40px;
  padding: 0px 16px;
  justify-content: center;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
  border-radius: ${({theme}) => theme.radius.medium};
  background-color: ${({theme, $isFocus}) =>
    $isFocus ? "none" : theme.color.neutralSurfaceBold};
  border: ${({theme, $isFocus, $isColorValid}) =>
    $isColorValid
        ? $isFocus && `1px solid ${theme.color.neutralBorderDefaultActive}`
        : `1px solid ${theme.color.dangerBorderDefault}`};

  & > span {
    width: 64px;
    font: ${({theme}) => theme.font.displayMedium12};
    color: ${({theme}) => theme.color.neutralTextWeak};
  }

  & > div {
    width: 112px;
    overflow: hidden;
    font: ${({theme}) => theme.font.displayMedium16};
    color: ${({theme}) => theme.color.neutralTextDefault};
    text-overflow: ellipsis;
  }
`;

const Buttons = styled.div`
  width: 100%;
  display: flex;
  justify-content: right;
  gap: 16px;
`;
